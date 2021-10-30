package yade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private final String title;
    private final int height, width;
    private Long glfwWindow;

    private static Window window = null;

    // Singleton class
    private Window() {
        this.width = 1920;
        this.height = 1080;
        this.title = "Mario";
    }

    public static Window get() {
        if(Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion());

        init();
        loop();
    }

    public void init() {
        // error callback
        GLFWErrorCallback.createPrint(System.err).set();

        if(!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }

        // config GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // create window, returns long which is the memory address where the window is in the memory
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL,  NULL);
        if(glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        // make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // important to create the bindings with C++
        GL.createCapabilities();
    }

    public void loop() {
        while(!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();
            glClearColor(1.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            glfwSwapBuffers(glfwWindow);
        }
    }
}
