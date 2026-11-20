package musheor.utils;

public final class pvfA15fQSVM {
   private static final int fStlZIvY9lS8 = (int)Math.round(250.0) + 1;
   private static final int QfZy2gWnKInxFPr = (int)Math.round(150.0) + 1;
   private final double TJImwK2;
   private final double wSJeYsJcmjNZf;
   private final float[] i2Nu6XHKic = new float[fStlZIvY9lS8 * QfZy2gWnKInxFPr];
   private double vGAMA89H;

   private pvfA15fQSVM(double gravity, double jumpVelocity) {
      this.TJImwK2 = gravity;
      this.wSJeYsJcmjNZf = jumpVelocity;
   }

   public static pvfA15fQSVM fStlZIvY9lS8(double gravity, double jumpVelocity) {
      pvfA15fQSVM opt = new pvfA15fQSVM(gravity, jumpVelocity);
      opt.vGAMA89H = opt.QfZy2gWnKInxFPr(72.4);

      for (int i = 0; i < 4; i++) {
         opt.fStlZIvY9lS8();
         double next = opt.QfZy2gWnKInxFPr();
         if (Math.abs(next - opt.vGAMA89H) < 1.0E-7) {
            opt.vGAMA89H = next;
            break;
         }

         opt.vGAMA89H = next;
      }

      return opt;
   }

   public float fStlZIvY9lS8(double heightAboveGround, double vy, boolean onGround) {
      double y = Math.max(0.0, heightAboveGround);
      if (onGround) {
         y = 0.0;
         vy = Math.max(this.wSJeYsJcmjNZf, vy);
      }

      double bestPitch = 90.0;
      double bestScore = Double.NEGATIVE_INFINITY;

      for (double p = 90.0; p >= 0.0; p -= 0.5) {
         double score = this.fStlZIvY9lS8(y, vy, fStlZIvY9lS8(p));
         if (score > bestScore) {
            bestScore = score;
            bestPitch = p;
         }
      }

      return (float)bestPitch;
   }

   private void fStlZIvY9lS8() {
      int actions = (int)Math.round(45.0) + 1;
      double[] cos2 = new double[actions];

      for (int a = 0; a < actions; a++) {
         cos2[a] = fStlZIvY9lS8(90.0 - a * 2.0);
      }

      float[] next = new float[this.i2Nu6XHKic.length];

      for (int iter = 0; iter < 200; iter++) {
         double maxDelta = 0.0;

         for (int iy = 0; iy < fStlZIvY9lS8; iy++) {
            double y = iy * 0.01;

            for (int iv = 0; iv < QfZy2gWnKInxFPr; iv++) {
               double vy = -1.0 + iv * 0.01;
               double best = Double.NEGATIVE_INFINITY;

               for (int a = 0; a < actions; a++) {
                  double s = this.fStlZIvY9lS8(y, vy, cos2[a]);
                  if (s > best) {
                     best = s;
                  }
               }

               int idx = iy * QfZy2gWnKInxFPr + iv;
               next[idx] = (float)best;
               maxDelta = Math.max(maxDelta, Math.abs(best - this.i2Nu6XHKic[idx]));
            }
         }

         System.arraycopy(next, 0, this.i2Nu6XHKic, 0, this.i2Nu6XHKic.length);
         if (maxDelta < 1.0E-7) {
            break;
         }
      }
   }

   private double fStlZIvY9lS8(double y, double vy, double h) {
      double mid = vy + this.TJImwK2 * (-1.0 + h * 0.75);
      double lift = mid < 0.0 ? mid * -0.1 * h : 0.0;
      double vyNext = (mid + lift) * 0.98F;
      double yNext = y + vyNext;
      double reward = 0.9 * lift - this.vGAMA89H;
      return yNext <= 0.0 ? reward : reward + this.QfZy2gWnKInxFPr(yNext, vyNext);
   }

   private static double fStlZIvY9lS8(double pitch) {
      double c = Math.cos((float)pitch * (float) (Math.PI / 180.0));
      return c * c;
   }

   private double QfZy2gWnKInxFPr(double y, double vy) {
      double fy = Math.min(Math.max(y / 0.01, 0.0), fStlZIvY9lS8 - 1.0001);
      double fv = Math.min(Math.max((vy - -1.0) / 0.01, 0.0), QfZy2gWnKInxFPr - 1.0001);
      int iy = (int)fy;
      int iv = (int)fv;
      double ty = fy - iy;
      double tv = fv - iv;
      int i = iy * QfZy2gWnKInxFPr + iv;
      double a = this.i2Nu6XHKic[i] + (this.i2Nu6XHKic[i + 1] - this.i2Nu6XHKic[i]) * tv;
      double b = this.i2Nu6XHKic[i + QfZy2gWnKInxFPr] + (this.i2Nu6XHKic[i + QfZy2gWnKInxFPr + 1] - this.i2Nu6XHKic[i + QfZy2gWnKInxFPr]) * tv;
      return a + (b - a) * ty;
   }

   private double QfZy2gWnKInxFPr() {
      double y = 0.0;
      double vy = 0.0;
      double boost = 0.2;
      boolean ground = true;

      for (int t = 1; t < 400; t++) {
         double p = this.fStlZIvY9lS8(y, vy, ground);
         if (ground) {
            vy = Math.max(this.wSJeYsJcmjNZf, vy);
            ground = false;
         }

         double[] s = this.TJImwK2(vy, p);
         vy = s[0];
         boost += 0.9 * s[1];
         y += vy;
         if (y <= 0.0) {
            return boost / t;
         }
      }

      return this.vGAMA89H;
   }

   private double QfZy2gWnKInxFPr(double pitch) {
      double y = 0.0;
      double vy = this.wSJeYsJcmjNZf;
      double boost = 0.2;

      for (int t = 1; t < 400; t++) {
         double[] s = this.TJImwK2(vy, pitch);
         vy = s[0];
         boost += 0.9 * s[1];
         y += vy;
         if (y <= 0.0) {
            return boost / t;
         }
      }

      return 0.0;
   }

   private double[] TJImwK2(double vy, double pitch) {
      double h = fStlZIvY9lS8(pitch);
      double mid = vy + this.TJImwK2 * (-1.0 + h * 0.75);
      double lift = mid < 0.0 ? mid * -0.1 * h : 0.0;
      return new double[]{(mid + lift) * 0.98F, lift};
   }
}
