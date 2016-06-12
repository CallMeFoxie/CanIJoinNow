package foxie.canijoinnow;

import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.io.File;

public class Events {
   public static Events INSTANCE;

   public Events() {
      INSTANCE = this;
   }

   PropertyManager settings;
   String baseMOTD = "";

   public void preinit() {
      settings = new PropertyManager(new File("server.properties"));
      baseMOTD = settings.getStringProperty("motd", "A Minecraft Server");
   }

   public void init() {
      MinecraftForge.EVENT_BUS.register(INSTANCE);
   }

   public void postinit() {
   }

   public void serverStarted(FMLServerStartedEvent event) {

   }

   @SubscribeEvent
   public void serverTick(TickEvent.ServerTickEvent event) {
      if (event.phase != TickEvent.Phase.START)
         return;

      WorldServer server = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(0);

      long ticks = server.getWorldTime() % 24000;

      int hours = (int) (ticks / 1000), minutes = (int) ((ticks % 1000) * 3 / 50);

      ServerStatusResponse response = FMLCommonHandler.instance().getMinecraftServerInstance().getServerStatusResponse();
      TextFormatting colour;
      if (hours < 12)
         colour = TextFormatting.GREEN;
      else
         colour = TextFormatting.RED;

      // wrap around 24h clock
      hours += 6;
      if (hours > 24)
         hours -= 24;

      response.setServerDescription(new TextComponentString(baseMOTD + " " + colour +
              String.format("%02d", hours) + ":" + String.format("%02d", minutes)));
   }
}