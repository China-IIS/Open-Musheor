package musheor.utils;

import meteordevelopment.meteorclient.MeteorClient;
import meteordevelopment.meteorclient.systems.modules.Modules;
import musheor.modules.automation.HighwayBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.Items;

public class oX7ku {
   public static int fStlZIvY9lS8 = 0;

   public static String fStlZIvY9lS8(int sectionSize) {
      double distanceLeft = TJImwK2(sectionSize);
      double percentage = (sectionSize - distanceLeft) / sectionSize * 100.0;
      return String.format("%.2f", percentage);
   }

   public static int fStlZIvY9lS8() {
      oyDPiOVBGKG.gVOhtBej1x direction = HighwayBuilder.i2Nu6XHKic();

      assert MeteorClient.mc.player != null;

      int distanceTravelled = 0;
      if (direction == oyDPiOVBGKG.gVOhtBej1x.i2Nu6XHKic || direction == oyDPiOVBGKG.gVOhtBej1x.fStlZIvY9lS8) {
         distanceTravelled = Math.abs(MeteorClient.mc.player.getBlockZ()) - Math.abs(obf.U6PQSXqW.fStlZIvY9lS8().vGAMA89H());
      } else if (direction == oyDPiOVBGKG.gVOhtBej1x.qhulAUwd || direction == oyDPiOVBGKG.gVOhtBej1x.TJImwK2) {
         distanceTravelled = Math.abs(MeteorClient.mc.player.getBlockX()) - Math.abs(obf.U6PQSXqW.fStlZIvY9lS8().i2Nu6XHKic());
      }

      if (direction == oyDPiOVBGKG.gVOhtBej1x.vGAMA89H
         || direction == oyDPiOVBGKG.gVOhtBej1x.wSJeYsJcmjNZf
         || direction == oyDPiOVBGKG.gVOhtBej1x.BrmKXSYhWhqoB1
         || direction == oyDPiOVBGKG.gVOhtBej1x.QfZy2gWnKInxFPr) {
         distanceTravelled = Math.abs(Math.abs(MeteorClient.mc.player.getBlockX()) - Math.abs(obf.U6PQSXqW.fStlZIvY9lS8().i2Nu6XHKic()));
      }

      return Math.abs(distanceTravelled);
   }

   public static int QfZy2gWnKInxFPr() {
      if (((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
         fStlZIvY9lS8 = fStlZIvY9lS8();
      }

      return fStlZIvY9lS8;
   }

   public static double TJImwK2() {
      if (!((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
         return -1.0;
      } else {
         double obsidianPlaced = obf.U6PQSXqW.fStlZIvY9lS8().DqGSCWplcjMi();
         double ticksPassed = obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7();
         return obsidianPlaced != 0.0 && ticksPassed != 0.0 ? obsidianPlaced / (ticksPassed / 20.0) : 0.0;
      }
   }

   public static double wSJeYsJcmjNZf() {
      if (!((HighwayBuilder)Modules.get().get(HighwayBuilder.class)).isActive()) {
         return -1.0;
      } else {
         obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
         double blocksBroken = state.g0iiTDJzfIGXdui() + state.RE8q1TMDRWx() + state.HomZ7BbhM7wB();
         double ticksPassed = state.pF16zz4NJECfg7();
         return blocksBroken != 0.0 && ticksPassed != 0.0 ? blocksBroken / (ticksPassed / 20.0) : 0.0;
      }
   }

   public static int i2Nu6XHKic() {
      return musheor.utils.manager.PathKit.vGAMA89H(Items.ENDER_CHEST) * 8 + musheor.utils.manager.PathKit.vGAMA89H(Items.OBSIDIAN);
   }

   public static double fStlZIvY9lS8(double distanceTravelled, double ticksPassed) {
      double secondsPassed = ticksPassed / 20.0;
      return secondsPassed <= 0.0 ? 0.0 : distanceTravelled / secondsPassed;
   }

   public static double QfZy2gWnKInxFPr(int sectionSize) {
      obf.U6PQSXqW state = obf.U6PQSXqW.fStlZIvY9lS8();
      if (QfZy2gWnKInxFPr() >= 1 && state.pF16zz4NJECfg7() >= 1) {
         long remainingDistance = TJImwK2(sectionSize);
         long distanceTravelled = QfZy2gWnKInxFPr();
         long distancePerSecond = QfZy2gWnKInxFPr() / (state.pF16zz4NJECfg7() / 20);
         if (distanceTravelled <= 1L) {
            return -2.0;
         } else {
            return state.pF16zz4NJECfg7() <= 200 ? -1.0 : remainingDistance / fStlZIvY9lS8(distanceTravelled, state.pF16zz4NJECfg7());
         }
      } else {
         return 0.0;
      }
   }

   public static int TJImwK2(int sectionSize) {
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.IndexOutOfBoundsException: Index 3 out of bounds for length 3
      //   at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:100)
      //   at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:106)
      //   at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:302)
      //   at java.base/java.util.Objects.checkIndex(Objects.java:385)
      //   at java.base/java.util.ArrayList.get(ArrayList.java:427)
      //   at org.jetbrains.java.decompiler.modules.decompiler.SwitchPatternMatchProcessor.processStatement(SwitchPatternMatchProcessor.java:280)
      //   at org.jetbrains.java.decompiler.modules.decompiler.SwitchPatternMatchProcessor.processPatternMatchingRec(SwitchPatternMatchProcessor.java:42)
      //   at org.jetbrains.java.decompiler.modules.decompiler.SwitchPatternMatchProcessor.processPatternMatchingRec(SwitchPatternMatchProcessor.java:37)
      //   at org.jetbrains.java.decompiler.modules.decompiler.SwitchPatternMatchProcessor.processPatternMatchingRec(SwitchPatternMatchProcessor.java:37)
      //   at org.jetbrains.java.decompiler.modules.decompiler.SwitchPatternMatchProcessor.processPatternMatchingRec(SwitchPatternMatchProcessor.java:37)
      //   at org.jetbrains.java.decompiler.modules.decompiler.SwitchPatternMatchProcessor.processPatternMatching(SwitchPatternMatchProcessor.java:23)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:330)
      //
      // Bytecode:
      // 00: getstatic musheor/utils/oX7ku.QfZy2gWnKInxFPr Z
      // 03: ifne 17
      // 06: getstatic meteordevelopment/meteorclient/MeteorClient.mc Lnet/minecraft/MinecraftClient;
      // 09: getfield net/minecraft/MinecraftClient.player Lnet/minecraft/ClientPlayerEntity;
      // 0c: ifnonnull 17
      // 0f: new java/lang/AssertionError
      // 12: dup
      // 13: invokespecial java/lang/AssertionError.<init> ()V
      // 16: athrow
      // 17: invokestatic musheor/modules/automation/HighwayBuilder.i2Nu6XHKic ()Lmusheor/utils/oyDPiOVBGKG$gVOhtBej1x;
      // 1a: astore 1
      // 1b: aload 1
      // 1c: astore 3
      // 1d: bipush 0
      // 1e: istore 4
      // 20: aload 3
      // 21: iload 4
      // 23: invokedynamic enumSwitch (Lmusheor/utils/oyDPiOVBGKG$gVOhtBej1x;I)I bsm=java/lang/runtime/SwitchBootstraps.enumSwitch (Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; args=[ "NORTH", "SOUTH", "EAST", "WEST", "NORTH_EAST", "NORTH_WEST", "SOUTH_EAST", "SOUTH_WEST" ]
      // 28: tableswitch 78 -1 7 78 52 52 65 65 65 65 65 65
      // 5c: getstatic meteordevelopment/meteorclient/MeteorClient.mc Lnet/minecraft/MinecraftClient;
      // 5f: getfield net/minecraft/MinecraftClient.player Lnet/minecraft/ClientPlayerEntity;
      // 62: invokevirtual net/minecraft/ClientPlayerEntity.getBlockZ ()I
      // 65: istore 2
      // 66: goto 78
      // 69: getstatic meteordevelopment/meteorclient/MeteorClient.mc Lnet/minecraft/MinecraftClient;
      // 6c: getfield net/minecraft/MinecraftClient.player Lnet/minecraft/ClientPlayerEntity;
      // 6f: invokevirtual net/minecraft/ClientPlayerEntity.getBlockX ()I
      // 72: istore 2
      // 73: goto 78
      // 76: bipush 0
      // 77: istore 2
      // 78: iload 2
      // 79: iload 0
      // 7a: invokestatic java/lang/Math.floorDiv (II)I
      // 7d: iload 0
      // 7e: imul
      // 7f: istore 3
      // 80: iload 3
      // 81: iload 0
      // 82: iadd
      // 83: istore 4
      // 85: aload 1
      // 86: getstatic musheor/utils/oyDPiOVBGKG$gVOhtBej1x.fStlZIvY9lS8 Lmusheor/utils/oyDPiOVBGKG$gVOhtBej1x;
      // 89: if_acmpeq a1
      // 8c: aload 1
      // 8d: getstatic musheor/utils/oyDPiOVBGKG$gVOhtBej1x.TJImwK2 Lmusheor/utils/oyDPiOVBGKG$gVOhtBej1x;
      // 90: if_acmpeq a1
      // 93: aload 1
      // 94: getstatic musheor/utils/oyDPiOVBGKG$gVOhtBej1x.vGAMA89H Lmusheor/utils/oyDPiOVBGKG$gVOhtBej1x;
      // 97: if_acmpeq a1
      // 9a: aload 1
      // 9b: getstatic musheor/utils/oyDPiOVBGKG$gVOhtBej1x.BrmKXSYhWhqoB1 Lmusheor/utils/oyDPiOVBGKG$gVOhtBej1x;
      // 9e: if_acmpne a4
      // a1: iload 4
      // a3: istore 3
      // a4: iload 3
      // a5: iload 2
      // a6: isub
      // a7: istore 5
      // a9: iload 5
      // ab: invokestatic java/lang/Math.abs (I)I
      // ae: ireturn
   }

   public static String wSJeYsJcmjNZf(int value) {
      if (value >= 1000000) {
         return String.format("%.2fm", value / 1000000.0);
      } else {
         return value >= 1000 ? String.format("%.2fk", value / 1000.0) : String.valueOf(value);
      }
   }

   public static String fStlZIvY9lS8(double placementsPerSecond) {
      return placementsPerSecond == -1.0 ? "Waiting..." : String.format("%.2f blocks / s", placementsPerSecond);
   }

   public static String QfZy2gWnKInxFPr(double placementsPerSecond) {
      return placementsPerSecond == -1.0 ? "Waiting..." : String.format("%.2f blocks / h", placementsPerSecond * 3600.0);
   }

   public static String i2Nu6XHKic(int distanceTravelled) {
      return distanceTravelled < 1
         ? "Waiting..."
         : String.format("%.2f blocks / s", (float)fStlZIvY9lS8(distanceTravelled, obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7()));
   }

   public static String vGAMA89H(int distanceTravelled) {
      return distanceTravelled < 1
         ? "Waiting..."
         : String.format("%.2f blocks / h", (float)fStlZIvY9lS8(distanceTravelled, obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7()) * 3600.0F);
   }

   public static String TJImwK2(double breakingPerSecond) {
      return breakingPerSecond == -1.0 ? "Waiting..." : String.format("%.2f blocks / s", breakingPerSecond);
   }

   public static String vGAMA89H() {
      if (TJImwK2() == -1.0) {
         return "Waiting...";
      } else {
         long timeLeft = (long)(i2Nu6XHKic() / TJImwK2());
         long hours = timeLeft / 3600L;
         long minutes = timeLeft % 3600L / 60L;
         long seconds = timeLeft % 60L;
         return String.format("%d:%02d:%02d", hours, minutes, seconds);
      }
   }

   public static String qhulAUwd(int sectionSize) {
      if (TJImwK2() == -1.0) {
         return "Waiting...";
      } else {
         long timeLeft = (long)(TJImwK2(sectionSize) / fStlZIvY9lS8(QfZy2gWnKInxFPr(), obf.U6PQSXqW.fStlZIvY9lS8().pF16zz4NJECfg7()));
         long hours = timeLeft / 3600L;
         long minutes = timeLeft % 3600L / 60L;
         long seconds = timeLeft % 60L;
         return String.format("%d:%02d:%02d", hours, minutes, seconds);
      }
   }

   public static String fStlZIvY9lS8(long ticks) {
      if (ticks == -1L) {
         return "Calculating...";
      } else if (ticks == -2L) {
         return "Waiting...";
      } else {
         long totalSeconds = ticks / 20L;
         long hours = totalSeconds / 3600L;
         long minutes = totalSeconds % 3600L / 60L;
         long seconds = totalSeconds % 60L;
         return String.format("%d:%02d:%02d", hours, minutes, seconds);
      }
   }
}
