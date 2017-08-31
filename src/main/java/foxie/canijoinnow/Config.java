package foxie.canijoinnow;


import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {

   public static int dimID = 0;

   private Configuration cfg;
   public String dateformat = "%h:%m";
   public int weeklength = 7;

   public Config(File file) {
      cfg = new Configuration(file);
   }

   public void preinit() {
      dimID = this.cfg.getInt("dimension", "config", dimID, Integer.MIN_VALUE, Integer.MAX_VALUE, "Dimension ID to show in MoTD");
      dateformat = this.cfg.getString("dateformat", "config", dateformat, "format of the date. %d, %w, %h, %m, %o");
      /*
       * d = day
       * m = month -- NOT ADDED (requires months)
       * y = year -- NOT ADDED (requires months)
       * w = week
       * h = hour
       * m = minute
       * o = day of week
       */
      weeklength = this.cfg.getInt("weeklength", "config", weeklength, 1, Integer.MAX_VALUE, "Length (days) of week, for %w format");
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
