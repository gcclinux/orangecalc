package wagemaker;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.HeadlessException;

/**
 * Utility class for calculating optimal window dimensions based on system display characteristics.
 * Provides cross-platform window sizing to ensure consistent appearance across different systems.
 * 
 * @author OrangeCalc Team
 * @version 1.0
 */
public class WindowSizeCalculator {
    
    // Base dimensions - reference size from Linux laptop
    private static final int BASE_WIDTH = 473;
    private static final int BASE_HEIGHT = 480;
    
    // Standard DPI reference (typical for older displays)
    private static final int STANDARD_DPI = 96;
    
    // Scaling constants for different operating systems
    private static final double WINDOWS_SCALING_ADJUSTMENT = 1.03; // Accounts for Windows-specific differences
    private static final double LINUX_SCALING_ADJUSTMENT = 1.0;    // Reference platform
    private static final double MAC_SCALING_ADJUSTMENT = 1.02;     // For future macOS support
    
    /**
     * Calculates the optimal window size for the current system configuration.
     * 
     * @return Dimension object with calculated width and height
     */
    public static Dimension calculateOptimalSize() {
        try {
            // Check for system property overrides first
            Dimension overrideSize = checkForOverrides();
            if (overrideSize != null) {
                logDebug("Using override dimensions: " + overrideSize.width + "x" + overrideSize.height);
                return overrideSize;
            }
            
            // Get display configuration
            DisplayConfiguration config = getDisplayConfiguration();
            if (config == null) {
                logWarning("Display configuration is null, using fallback dimensions");
                return getBaseSize();
            }
            
            logDebug("Detected display configuration: " + config.toString());
            
            // Calculate base size
            Dimension baseSize = getBaseSize();
            
            // Apply scaling factor
            double scalingFactor = getScalingFactor(config);
            
            // Validate scaling factor
            if (Double.isNaN(scalingFactor) || Double.isInfinite(scalingFactor) || scalingFactor <= 0) {
                logWarning("Invalid scaling factor: " + scalingFactor + ", using default scaling");
                scalingFactor = 1.0;
            }
            
            int scaledWidth = (int) Math.round(baseSize.width * scalingFactor);
            int scaledHeight = (int) Math.round(baseSize.height * scalingFactor);
            
            // Validate calculated dimensions
            if (scaledWidth <= 0 || scaledHeight <= 0 || scaledWidth > 4000 || scaledHeight > 3000) {
                logWarning("Calculated dimensions out of range (" + scaledWidth + "x" + scaledHeight + "), using base size");
                return getBaseSize();
            }
            
            Dimension result = new Dimension(scaledWidth, scaledHeight);
            logDebug("Calculated optimal size: " + result.width + "x" + result.height + " (scaling factor: " + String.format("%.2f", scalingFactor) + ")");
            
            return result;
            
        } catch (HeadlessException e) {
            logWarning("Running in headless environment, using base dimensions");
            return getBaseSize();
        } catch (SecurityException e) {
            logWarning("Security restrictions prevent display detection, using base dimensions");
            return getBaseSize();
        } catch (Exception e) {
            logWarning("Unexpected error calculating window size: " + e.getMessage() + ", using base dimensions");
            return getBaseSize();
        }
    }
    
    /**
     * Gets the display configuration for the current system.
     * 
     * @return DisplayConfiguration object with system display information
     */
    private static DisplayConfiguration getDisplayConfiguration() {
        try {
            // Get system DPI
            int dpi = Toolkit.getDefaultToolkit().getScreenResolution();
            
            // Get operating system name
            String osName = System.getProperty("os.name", "Unknown");
            
            // Get screen size
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            
            // Calculate initial scaling factor (will be refined in getScalingFactor method)
            double initialScalingFactor = (double) dpi / STANDARD_DPI;
            
            return new DisplayConfiguration(dpi, osName, screenSize, initialScalingFactor);
            
        } catch (HeadlessException e) {
            System.err.println("Warning: Running in headless environment, using default configuration");
            return createDefaultConfiguration();
        } catch (SecurityException e) {
            System.err.println("Warning: Security restrictions prevent display detection, using default configuration");
            return createDefaultConfiguration();
        } catch (Exception e) {
            System.err.println("Warning: Unexpected error during display detection: " + e.getMessage());
            return createDefaultConfiguration();
        }
    }
    
    /**
     * Creates a default display configuration for fallback scenarios.
     * 
     * @return Default DisplayConfiguration object
     */
    private static DisplayConfiguration createDefaultConfiguration() {
        return new DisplayConfiguration(
            STANDARD_DPI, 
            "Unknown", 
            new Dimension(1920, 1080), // Assume common resolution
            1.0 // No scaling
        );
    }
    
    /**
     * Gets the base window dimensions.
     * 
     * @return Dimension object with base width and height
     */
    private static Dimension getBaseSize() {
        return new Dimension(BASE_WIDTH, BASE_HEIGHT);
    }
    
    /**
     * Calculates the scaling factor based on display configuration.
     * 
     * @param config The display configuration
     * @return The scaling factor to apply to base dimensions
     */
    private static double getScalingFactor(DisplayConfiguration config) {
        if (config == null) {
            return 1.0;
        }
        
        // Start with DPI-based scaling
        double dpiScaling = (double) config.getDpi() / STANDARD_DPI;
        
        // Apply OS-specific adjustments based on your observations
        double osAdjustment = 1.0;
        if (config.isWindows()) {
            // Windows systems (like your 4K monitor) need slightly larger width
            osAdjustment = WINDOWS_SCALING_ADJUSTMENT;
        } else if (config.isLinux()) {
            // Linux is our reference platform
            osAdjustment = LINUX_SCALING_ADJUSTMENT;
        } else if (config.getOsName().toLowerCase().contains("mac")) {
            // Future macOS support
            osAdjustment = MAC_SCALING_ADJUSTMENT;
        }
        
        // Handle high-DPI displays more conservatively
        if (config.isHighDpi()) {
            // For high-DPI displays, we want more conservative scaling
            // to avoid making the window too large
            dpiScaling = Math.min(dpiScaling, 1.5); // Cap at 150% scaling
            
            // Apply a smoothing factor for very high DPI
            if (config.getDpi() > 150) {
                dpiScaling = 1.0 + (dpiScaling - 1.0) * 0.7; // Reduce scaling impact
            }
        }
        
        // Combine DPI scaling with OS adjustment
        double finalScaling = dpiScaling * osAdjustment;
        
        // Ensure scaling stays within reasonable bounds
        finalScaling = Math.max(0.8, Math.min(finalScaling, 2.0));
        
        return finalScaling;
    }
    
    /**
     * Checks for system property overrides for window dimensions.
     * Supports the following system properties:
     * - orangecalc.window.width.override: Force specific width
     * - orangecalc.window.height.override: Force specific height
     * - orangecalc.window.scaling.disabled: Disable automatic scaling
     * 
     * @return Dimension object if overrides are found, null otherwise
     */
    private static Dimension checkForOverrides() {
        try {
            String widthOverride = System.getProperty("orangecalc.window.width.override");
            String heightOverride = System.getProperty("orangecalc.window.height.override");
            String scalingDisabled = System.getProperty("orangecalc.window.scaling.disabled");
            
            // If scaling is disabled, return base size
            if ("true".equalsIgnoreCase(scalingDisabled)) {
                logDebug("Scaling disabled via system property, using base dimensions");
                return getBaseSize();
            }
            
            // If both width and height overrides are provided
            if (widthOverride != null && heightOverride != null) {
                try {
                    int width = Integer.parseInt(widthOverride.trim());
                    int height = Integer.parseInt(heightOverride.trim());
                    
                    // Validate override values
                    if (width > 0 && height > 0 && width <= 4000 && height <= 3000) {
                        logDebug("Using dimension overrides: " + width + "x" + height);
                        return new Dimension(width, height);
                    } else {
                        logWarning("Override dimensions out of valid range (" + width + "x" + height + "), ignoring");
                    }
                } catch (NumberFormatException e) {
                    logWarning("Invalid override values, ignoring. Width: '" + widthOverride + "', Height: '" + heightOverride + "'");
                }
            } else if (widthOverride != null || heightOverride != null) {
                logWarning("Only one dimension override provided (width: " + widthOverride + ", height: " + heightOverride + "). Both width and height must be specified.");
            }
            
        } catch (SecurityException e) {
            logWarning("Cannot access system properties for overrides: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Logs a debug message if debug mode is enabled.
     * 
     * @param message The debug message to log
     */
    private static void logDebug(String message) {
        if (isDebugEnabled()) {
            System.out.println("[WindowSizeCalculator DEBUG] " + message);
        }
    }
    
    /**
     * Logs a warning message.
     * 
     * @param message The warning message to log
     */
    private static void logWarning(String message) {
        System.err.println("[WindowSizeCalculator WARNING] " + message);
    }
    
    /**
     * Checks if debug logging is enabled via system property.
     * 
     * @return true if debug logging is enabled, false otherwise
     */
    private static boolean isDebugEnabled() {
        try {
            return "true".equalsIgnoreCase(System.getProperty("orangecalc.debug", "false"));
        } catch (SecurityException e) {
            return false;
        }
    }
    
    /**
     * Displays comprehensive diagnostic information about the current display configuration.
     * This method can be called to troubleshoot window sizing issues.
     * Enable via system property: -Dorangecalc.diagnostics=true
     */
    public static void displayDiagnostics() {
        try {
            boolean diagnosticsEnabled = "true".equalsIgnoreCase(System.getProperty("orangecalc.diagnostics", "false"));
            if (!diagnosticsEnabled) {
                return;
            }
            
            System.out.println("=== OrangeCalc Window Sizing Diagnostics ===");
            
            // Display configuration
            DisplayConfiguration config = getDisplayConfiguration();
            if (config != null) {
                System.out.println("Display Configuration: " + config.toString());
                System.out.println("  - High DPI: " + config.isHighDpi());
                System.out.println("  - Windows OS: " + config.isWindows());
                System.out.println("  - Linux OS: " + config.isLinux());
            } else {
                System.out.println("Display Configuration: Failed to detect");
            }
            
            // Calculated dimensions
            Dimension calculatedSize = calculateOptimalSize();
            System.out.println("Calculated Window Size: " + calculatedSize.width + "x" + calculatedSize.height);
            
            // Base dimensions
            Dimension baseSize = getBaseSize();
            System.out.println("Base Window Size: " + baseSize.width + "x" + baseSize.height);
            
            // Scaling factor
            if (config != null) {
                double scalingFactor = getScalingFactor(config);
                System.out.println("Applied Scaling Factor: " + String.format("%.3f", scalingFactor));
            }
            
            // System properties
            System.out.println("System Properties:");
            printSystemProperty("  orangecalc.window.width.override");
            printSystemProperty("  orangecalc.window.height.override");
            printSystemProperty("  orangecalc.window.scaling.disabled");
            printSystemProperty("  orangecalc.debug");
            printSystemProperty("  orangecalc.diagnostics");
            
            // Java/System info
            System.out.println("Java Version: " + System.getProperty("java.version", "Unknown"));
            System.out.println("OS Name: " + System.getProperty("os.name", "Unknown"));
            System.out.println("OS Version: " + System.getProperty("os.version", "Unknown"));
            
            System.out.println("=== End Diagnostics ===");
            
        } catch (Exception e) {
            System.err.println("Error displaying diagnostics: " + e.getMessage());
        }
    }
    
    /**
     * Helper method to safely print system property values.
     * 
     * @param propertyName The name of the system property to print
     */
    private static void printSystemProperty(String propertyName) {
        try {
            String value = System.getProperty(propertyName.trim());
            System.out.println(propertyName + ": " + (value != null ? value : "not set"));
        } catch (SecurityException e) {
            System.out.println(propertyName + ": access denied");
        }
    }
}