package foxie.canijoinnow;

import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
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
   WorldServer server;

   public void preinit() {
      settings = new PropertyManager(new File("server.properties"));
      baseMOTD = settings.getStringProperty("motd", "A Minecraft Server");
   }

   public void init() {
      MinecraftForge.EVENT_BUS.register(INSTANCE);
   }

   public void postinit() {
   }

   private void loadServer() {
      try {
         server = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(Config.dimID);
      } catch (RuntimeException e) {
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

      long ticks = server.getWorldTime();

      ServerStatusResponse response = FMLCommonHandler.instance().getMinecraftServerInstance().getServerStatusResponse();
      TextFormatting colour;
      if (server.isDaytime())
         colour = TextFormatting.GREEN;
      else
         colour = TextFormatting.RED;

      response.setServerDescription(new TextComponentString(baseMOTD + " " + colour + format(ticks)));
   }

   private String format(long ticks) {
      long dayticks = ticks % 24000;
      int hours = ((int) (dayticks / 1000) + 6); // +6 to humanize
      if (hours >= 24)
         hours -= 24;

      int minutes = (int) ((dayticks % 1000) * 3 / 50);
      int day = (int) (ticks / 24000);
      int week = (day / CanIJoinNow.config.weeklength) + 1; // +1 to humanize
      int dayofweek = (day % CanIJoinNow.config.weeklength) + 1; // +1 to humanize
      day += 1; // +1 to humanize

      String formatted = CanIJoinNow.config.dateformat
              .replace("%d", String.format("%d", day))
              .replace("%w", String.format("%d", week))
              .replace("%h", String.format("%02d", hours))
              .replace("%m", String.format("%02d", minutes))
              .replace("%o", String.format("%d", dayofweek));

      return formatted;
   }
}
