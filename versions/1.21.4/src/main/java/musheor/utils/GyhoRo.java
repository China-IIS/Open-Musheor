package musheor.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.BlockPos;

public class GyhoRo {
   private static final Path fStlZIvY9lS8 = Path.of("meteor-client/pearls.json");
   private static final Gson QfZy2gWnKInxFPr = new GsonBuilder().setPrettyPrinting().create();
   private static final List<GyhoRo.gVOhtBej1x> TJImwK2 = new ArrayList<>();

   public static List<GyhoRo.gVOhtBej1x> fStlZIvY9lS8() {
      return TJImwK2;
   }

   public static void fStlZIvY9lS8(String ownerName, BlockPos trapdoorPos, int entityId) {
      for (GyhoRo.gVOhtBej1x r : TJImwK2) {
         if (r.QfZy2gWnKInxFPr.equals(trapdoorPos)) {
            r.fStlZIvY9lS8 = ownerName;
            r.TJImwK2 = entityId;
            TJImwK2();
            return;
         }
      }

      TJImwK2.add(new GyhoRo.gVOhtBej1x(ownerName, trapdoorPos, entityId));
      TJImwK2();
   }

   public static void fStlZIvY9lS8(BlockPos trapdoorPos, int entityId) {
      for (GyhoRo.gVOhtBej1x r : TJImwK2) {
         if (r.QfZy2gWnKInxFPr.equals(trapdoorPos)) {
            r.TJImwK2 = entityId;
            return;
         }
      }
   }

   public static GyhoRo.gVOhtBej1x fStlZIvY9lS8(BlockPos trapdoorPos) {
      for (GyhoRo.gVOhtBej1x r : TJImwK2) {
         if (r.QfZy2gWnKInxFPr.equals(trapdoorPos)) {
            return r;
         }
      }

      return null;
   }

   public static void fStlZIvY9lS8(String playerName) {
      TJImwK2.removeIf(r -> r.fStlZIvY9lS8.equalsIgnoreCase(playerName));
      TJImwK2();
   }

   public static void QfZy2gWnKInxFPr() {
      TJImwK2.clear();
      TJImwK2();
   }

   public static List<GyhoRo.gVOhtBej1x> QfZy2gWnKInxFPr(String ownerName) {
      List<GyhoRo.gVOhtBej1x> result = new ArrayList<>();

      for (GyhoRo.gVOhtBej1x r : TJImwK2) {
         if (r.fStlZIvY9lS8.equalsIgnoreCase(ownerName)) {
            result.add(r);
         }
      }

      return result;
   }

   public static void TJImwK2() {
      try {
         Files.createDirectories(fStlZIvY9lS8.getParent());
         JsonArray arr = new JsonArray();

         for (GyhoRo.gVOhtBej1x r : TJImwK2) {
            JsonObject obj = new JsonObject();
            obj.addProperty("owner", r.fStlZIvY9lS8);
            obj.addProperty("entityId", r.TJImwK2);
            obj.addProperty("x", r.QfZy2gWnKInxFPr.getX());
            obj.addProperty("y", r.QfZy2gWnKInxFPr.getY());
            obj.addProperty("z", r.QfZy2gWnKInxFPr.getZ());
            arr.add(obj);
         }

         Files.writeString(fStlZIvY9lS8, QfZy2gWnKInxFPr.toJson(arr));
      } catch (IOException var4) {
         var4.printStackTrace();
      }
   }

   public static void wSJeYsJcmjNZf() {
      TJImwK2.clear();
      if (Files.exists(fStlZIvY9lS8)) {
         try {
            String json = Files.readString(fStlZIvY9lS8);

            for (JsonElement el : JsonParser.parseString(json).getAsJsonArray()) {
               JsonObject obj = el.getAsJsonObject();
               String owner = obj.get("owner").getAsString();
               int entityId = obj.get("entityId").getAsInt();
               int x = obj.get("x").getAsInt();
               int y = obj.get("y").getAsInt();
               int z = obj.get("z").getAsInt();
               TJImwK2.add(new GyhoRo.gVOhtBej1x(owner, new BlockPos(x, y, z), entityId));
            }
         } catch (Exception var10) {
            var10.printStackTrace();
         }
      }
   }

   public static class gVOhtBej1x {
      public String fStlZIvY9lS8;
      public BlockPos QfZy2gWnKInxFPr;
      public int TJImwK2;

      public gVOhtBej1x(String ownerName, BlockPos trapdoorPos, int entityId) {
         this.fStlZIvY9lS8 = ownerName;
         this.QfZy2gWnKInxFPr = trapdoorPos;
         this.TJImwK2 = entityId;
      }
   }
}
