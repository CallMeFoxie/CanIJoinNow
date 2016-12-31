package foxie.canijoinnow;


import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

   public static int dimID = 0;

   private Configuration cfg;

   public Config(File file) {
      cfg = new Configuration(file);
   }

   public void preinit() {
      dimID = this.cfg.getInt("dimension", "config", dimID, -1000, 1000, "Dimension ID to show in MoTD");
   }

   public void init() {
   }

   public void postinit() {
      if (cfg.hasChanged())
         cfg.save();
   }

   public Configuration getConfig() {
      return cfg;
   }
}
