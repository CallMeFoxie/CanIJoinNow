package foxie.canijoinnow;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;

public class Events {
   public static Events INSTANCE;

   public Events() {
      INSTANCE = this;
   }

   PropertyManager settings;
   String baseMOTD = "";
   WorldServer server;

   public void preinit() {
      settings = new PropertyManager(new File("server.properties"));
      baseMOTD = settings.getStringProperty("motd", "A Minecraft Server");
   }

   public void init() {
      MinecraftForge.EVENT_BUS.register(INSTANCE);
      FMLCommonHandler.instance().bus().register(INSTANCE);
   }

   public void postinit() {
   }

   private void loadServer() {
      try {
         server = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(Config.dimID);
      } catch(RuntimeException e) {
         throw new RuntimeException("Dimension ID " + Config.dimID + " does not seem to exist!");
      }

      if (server == null)
         throw new RuntimeException("Dimension ID " + Config.dimID + " does not seem to exist!");
   }

   @SubscribeEvent
   public void serverTick(TickEvent.ServerTickEvent event) {
      if (event.phase != TickEvent.Phase.START)
         return;

      if (server == null)
         loadServer();

      long ticks = server.getWorldTime() % 24000;

      int hours = (int) (ticks / 1000), minutes = (int) ((ticks % 1000) * 3 / 50);

      ServerStatusResponse response = FMLCommonHandler.instance().getMinecraftServerInstance().func_147134_at();
      EnumChatFormatting colour;
      if (server.isDaytime())
         colour = EnumChatFormatting.GREEN;
      else
         colour = EnumChatFormatting.RED;

      // wrap around 24h clock
      hours += 6;
      if (hours >= 24)
         hours -= 24;

      response.func_151315_a(new ChatComponentText(baseMOTD + " " + colour +
              String.format("%02d", hours) + ":" + String.format("%02d", minutes)));
   }
}
