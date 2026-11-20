package musheor.modules.automation.highway;

import musheor.compat.VersionHelper;
import musheor.utils.oyDPiOVBGKG;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import obf.GyhoRo;

public class HighwayNavigator {
   private final MinecraftClient fStlZIvY9lS8 = MinecraftClient.getInstance();
   private final HighwayRouter.Route QfZy2gWnKInxFPr;
   private final HighwayNavigator.BounceController TJImwK2;
   private final HighwayNavigator.Listener wSJeYsJcmjNZf;
   private final HighwayNavigator.Config i2Nu6XHKic;
   private HighwayNavigator.State vGAMA89H = HighwayNavigator.State.fStlZIvY9lS8;
   private int qhulAUwd = 0;
   private int BrmKXSYhWhqoB1 = 0;
   private int TXWyNb = 0;
   private int I3psabmOrOI = 0;
   private oyDPiOVBGKG.pvfA15fQSVM duJ6sPeetAcBh6P = null;
   private double j376sIQ = Double.MAX_VALUE;

   public HighwayNavigator(
      HighwayRouter.Route route, HighwayNavigator.Config config, HighwayNavigator.BounceController bounceController, HighwayNavigator.Listener listener
   ) {
      this.QfZy2gWnKInxFPr = route;
      this.i2Nu6XHKic = config;
      this.TJImwK2 = bounceController;
      this.wSJeYsJcmjNZf = listener;
   }

   public HighwayNavigator.State fStlZIvY9lS8() {
      return this.vGAMA89H;
   }

   public void QfZy2gWnKInxFPr() {
      if (this.fStlZIvY9lS8.player != null && this.fStlZIvY9lS8.world != null) {
         if (this.vGAMA89H != HighwayNavigator.State.vGAMA89H && this.vGAMA89H != HighwayNavigator.State.qhulAUwd) {
            if (this.QfZy2gWnKInxFPr.fStlZIvY9lS8.isEmpty()) {
               this.umvNbb7iD9UpR();
            } else {
               switch (this.vGAMA89H) {
                  case fStlZIvY9lS8:
                     this.wSJeYsJcmjNZf();
                     break;
                  case QfZy2gWnKInxFPr:
                     this.i2Nu6XHKic();
                     break;
                  case TJImwK2:
                     this.vGAMA89H();
                     break;
                  case wSJeYsJcmjNZf:
                     this.qhulAUwd();
                     break;
                  case i2Nu6XHKic:
                     this.BrmKXSYhWhqoB1();
               }
            }
         }
      }
   }

   public void TJImwK2() {
      this.duJ6sPeetAcBh6P();
      if (GyhoRo.fStlZIvY9lS8()) {
         GyhoRo.wSJeYsJcmjNZf();
      }
   }

   private void wSJeYsJcmjNZf() {
      oyDPiOVBGKG.pvfA15fQSVM target = this.voxTwgNzyrpaAnP();
      float desiredYaw = this.TJImwK2(target);
      this.fStlZIvY9lS8.player.setYaw(desiredYaw);
      float yawDiff = Math.abs(this.fStlZIvY9lS8(this.fStlZIvY9lS8.player.getYaw(), desiredYaw));
      this.BrmKXSYhWhqoB1++;
      if (yawDiff <= this.i2Nu6XHKic.wSJeYsJcmjNZf() || this.BrmKXSYhWhqoB1 >= this.i2Nu6XHKic.TJImwK2()) {
         this.BrmKXSYhWhqoB1 = 0;
         this.j376sIQ = this.fStlZIvY9lS8(this.voxTwgNzyrpaAnP());
         this.I3psabmOrOI();
         this.fStlZIvY9lS8(HighwayNavigator.State.QfZy2gWnKInxFPr, "bouncing toward " + this.xileXsbo());
      }
   }

   private void i2Nu6XHKic() {
      oyDPiOVBGKG.pvfA15fQSVM target = this.voxTwgNzyrpaAnP();
      double dist = this.fStlZIvY9lS8(target);
      if (!GyhoRo.TJImwK2()) {
         this.fStlZIvY9lS8.player.setYaw(this.TJImwK2(target));
      }

      this.j376sIQ();
      if (dist < this.i2Nu6XHKic.fStlZIvY9lS8() && dist < this.j376sIQ) {
         this.fStlZIvY9lS8(HighwayNavigator.State.TJImwK2, "approaching " + this.xileXsbo());
      }
   }

   private void vGAMA89H() {
      oyDPiOVBGKG.pvfA15fQSVM target = this.voxTwgNzyrpaAnP();
      double dist = this.fStlZIvY9lS8(target);
      this.fStlZIvY9lS8.player.setYaw(this.TJImwK2(target));
      if (dist < this.i2Nu6XHKic.QfZy2gWnKInxFPr()) {
         this.duJ6sPeetAcBh6P();
         BlockPos goal = this.QfZy2gWnKInxFPr(target);
         GyhoRo.fStlZIvY9lS8(goal);
         this.fStlZIvY9lS8(HighwayNavigator.State.wSJeYsJcmjNZf, "transitioning at " + this.xileXsbo());
      }
   }

   private void qhulAUwd() {
      if (!GyhoRo.TJImwK2()) {
         this.I3psabmOrOI = this.i2Nu6XHKic.i2Nu6XHKic();
         this.fStlZIvY9lS8(HighwayNavigator.State.i2Nu6XHKic, "settling at " + this.xileXsbo());
      }
   }

   private void BrmKXSYhWhqoB1() {
      int nextWpIdx = this.qhulAUwd + 2;
      oyDPiOVBGKG.pvfA15fQSVM faceTarget = nextWpIdx < this.QfZy2gWnKInxFPr.TJImwK2.size()
         ? this.QfZy2gWnKInxFPr.TJImwK2.get(nextWpIdx)
         : this.voxTwgNzyrpaAnP();
      this.fStlZIvY9lS8.player.setYaw(this.TJImwK2(faceTarget));
      if (--this.I3psabmOrOI <= 0) {
         this.TXWyNb();
      }
   }

   private void TXWyNb() {
      this.qhulAUwd++;
      if (this.qhulAUwd >= this.QfZy2gWnKInxFPr.fStlZIvY9lS8.size()) {
         this.umvNbb7iD9UpR();
      } else {
         this.I3psabmOrOI();
         this.fStlZIvY9lS8(HighwayNavigator.State.fStlZIvY9lS8, "aligning for leg " + this.qhulAUwd + ": " + this.V7zKJ4n6D().TJImwK2().vGAMA89H());
      }
   }

   private void I3psabmOrOI() {
      this.TJImwK2.fStlZIvY9lS8(true);
   }

   private void duJ6sPeetAcBh6P() {
      this.TJImwK2.fStlZIvY9lS8(false);
   }

   private void j376sIQ() {
      oyDPiOVBGKG.pvfA15fQSVM pos = this.nOwp0NYl();
      if (this.duJ6sPeetAcBh6P != null) {
         double moved = HighwayNetwork.fStlZIvY9lS8(pos, this.duJ6sPeetAcBh6P);
         if (moved < 0.5) {
            this.TXWyNb++;
         } else {
            this.TXWyNb = 0;
         }
      }

      this.duJ6sPeetAcBh6P = pos;
   }

   private HighwayRouter.Leg V7zKJ4n6D() {
      return this.QfZy2gWnKInxFPr.fStlZIvY9lS8.get(this.qhulAUwd);
   }

   private oyDPiOVBGKG.pvfA15fQSVM voxTwgNzyrpaAnP() {
      return this.QfZy2gWnKInxFPr.TJImwK2.get(this.qhulAUwd + 1);
   }

   private String xileXsbo() {
      oyDPiOVBGKG.pvfA15fQSVM wp = this.voxTwgNzyrpaAnP();
      return String.format("(%.0f, %.0f)", wp.fStlZIvY9lS8(), wp.QfZy2gWnKInxFPr());
   }

   private double fStlZIvY9lS8(oyDPiOVBGKG.pvfA15fQSVM target) {
      oyDPiOVBGKG.pvfA15fQSVM pos = this.nOwp0NYl();
      return HighwayNetwork.fStlZIvY9lS8(pos, target);
   }

   private oyDPiOVBGKG.pvfA15fQSVM nOwp0NYl() {
      return new oyDPiOVBGKG.pvfA15fQSVM(this.fStlZIvY9lS8.player.getX(), this.fStlZIvY9lS8.player.getZ());
   }

   private BlockPos QfZy2gWnKInxFPr(oyDPiOVBGKG.pvfA15fQSVM p) {
      return new BlockPos((int)p.fStlZIvY9lS8(), (int)VersionHelper.get().getPlayerPos().getY(), (int)p.QfZy2gWnKInxFPr());
   }

   private float TJImwK2(oyDPiOVBGKG.pvfA15fQSVM target) {
      double dx = target.fStlZIvY9lS8() - this.fStlZIvY9lS8.player.getX();
      double dz = target.QfZy2gWnKInxFPr() - this.fStlZIvY9lS8.player.getZ();
      return (float)Math.toDegrees(Math.atan2(-dx, dz));
   }

   private float fStlZIvY9lS8(float a, float b) {
      float diff = (a - b) % 360.0F;
      if (diff > 180.0F) {
         diff -= 360.0F;
      }

      if (diff < -180.0F) {
         diff += 360.0F;
      }

      return diff;
   }

   private void fStlZIvY9lS8(HighwayNavigator.State newState, String description) {
      this.vGAMA89H = newState;
      this.wSJeYsJcmjNZf.fStlZIvY9lS8(newState, description);
   }

   private void umvNbb7iD9UpR() {
      this.duJ6sPeetAcBh6P();
      this.vGAMA89H = HighwayNavigator.State.vGAMA89H;
      this.wSJeYsJcmjNZf.fStlZIvY9lS8();
   }

   public interface BounceController {
      void fStlZIvY9lS8(boolean var1);
   }

   public record Config(double fStlZIvY9lS8, double QfZy2gWnKInxFPr, int TJImwK2, float wSJeYsJcmjNZf, int i2Nu6XHKic, int vGAMA89H) {
      public int i2Nu6XHKic() {
         return this.vGAMA89H;
      }
   
   }

   public interface Listener {
      void fStlZIvY9lS8(HighwayNavigator.State var1, String var2);

      void fStlZIvY9lS8();
   }

   public static enum State {
      fStlZIvY9lS8,
      QfZy2gWnKInxFPr,
      TJImwK2,
      wSJeYsJcmjNZf,
      i2Nu6XHKic,
      vGAMA89H,
      qhulAUwd;
   }
}
