package foxie.canijoinnow;


import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

   private Configuration cfg;

   public Config(File file) {
      cfg = new Configuration(file);

      if (cfg.hasChanged())
         cfg.save();

   }

   public void preinit() {
   }

   public void init() {
   }

   public void postinit() {
   }

   public Configuration getConfig() {
      return cfg;
   }
}
