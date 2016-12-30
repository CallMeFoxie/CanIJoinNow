package foxie.canijoinnow;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = CanIJoinNow.MODID, name = CanIJoinNow.NAME, version = CanIJoinNow.VERSION, acceptableRemoteVersions = "*")
public class CanIJoinNow {
   public static final String MODID   = "canijoinnow";
   public static final String NAME    = "Can I Join Now?";
   public static final String VERSION = "@VERSION@";

   @Mod.Instance(MODID)
   public static CanIJoinNow INSTANCE;

   private static Config config;

   private Events events;

   @Mod.EventHandler
   public void preinit(FMLPreInitializationEvent event) {
      if(event.getSide() != Side.SERVER)
         return;

      events = new Events();
      config = new Config(event.getSuggestedConfigurationFile());

      events.preinit();
   }

   @Mod.EventHandler
   public void init(FMLInitializationEvent event) {
      if(event.getSide() != Side.SERVER)
         return;

      events.init();
   }

   @Mod.EventHandler
   public void postinit(FMLPostInitializationEvent event) {
      if(event.getSide() != Side.SERVER)
         return;

      events.postinit();
   }

}
