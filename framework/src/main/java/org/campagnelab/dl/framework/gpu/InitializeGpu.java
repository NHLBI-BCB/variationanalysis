package org.campagnelab.dl.framework.gpu;

/**
 * Determine if the project was compiled with GPU support and initialize CUDA environment if so.
 * Created by fac2003 on 12/1/16.
 */
public class InitializeGpu {
    static Object initializer;

    public static void initialize() {
        try {
            System.out.println("Trying GPU initialization");
            // this class is provided by the gpus maven module if we are building with CUDA.
            Class<?> initializeCudaEnvironment = Class.forName("org.campagnelab.dl.gpus.InitializeCudaEnvironmentOnGPU");
            if (initializeCudaEnvironment != null) {

                // calling the constructor initializes the environment.
                initializer = initializeCudaEnvironment.newInstance();
                System.out.println("Done initializing");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found ");
            // ignore, CUDA is not found in this installation.
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialie GPU/CUDA environment.", e);
        }
    }
}
