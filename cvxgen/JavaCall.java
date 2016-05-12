public class JavaCall {
   static {
      System.loadLibrary("mpc"); // myjni.dll (Windows) or libmyjni.so (Unixes)
   }
 
   // Declare a native method sumAndAverage() that receives an int[] and
   //  return a double[2] array with [0] as sum and [1] as average
   private native double[] mpc(double[] numbers);
   private native void load();
 
   // Test Driver
   public static void main(String args[]) {
      double[] numbers = {0,0,0,-10,0,0};
      JavaCall jc = new JavaCall();
      jc.load();
      // long start = System.currentTimeMillis();
      double[] results = jc.mpc(numbers);
      // for (int i = 0; i < 999; i++) {
      // 	results = jc.mpc(numbers);
      // }
      // start = System.currentTimeMillis()-start;
      // System.out.println("time " + start);
      System.out.println("u0 " + results[0]);
      System.out.println("u1 " + results[1]);
   }
}