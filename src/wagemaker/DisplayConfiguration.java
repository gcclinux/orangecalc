package wagemaker;

import java.awt.Dimension;

/**
 * Data class that holds display-related system information for cross-platform window sizing.
 * 
 * @author OrangeCalc Team
 * @version 1.0
 */
public class DisplayConfiguration {
    private final int dpi;
    private final String osName;
    private final Dimension screenSize;
    private final double scalingFactor;

    /**
     * Creates a new DisplayConfiguration with the specified parameters.
     * 
     * @param dpi The screen DPI (dots per inch)
     * @param osName The operating system name
     * @param screenSize The screen dimensions
     * @param scalingFactor The calculated scaling factor for this configuration
     */
    public DisplayConfiguration(int dpi, String osName, Dimension screenSize, double scalingFactor) {
        this.dpi = dpi;
        this.osName = osName;
        this.screenSize = new Dimension(screenSize); // Defensive copy
        this.scalingFactor = scalingFactor;
    }

    /**
     * Gets the screen DPI.
     * 
     * @return The DPI value
     */
    public int getDpi() {
        return dpi;
    }

    /**
     * Gets the operating system name.
     * 
     * @return The OS name
     */
    public String getOsName() {
        return osName;
    }

    /**
     * Gets the screen size.
     * 
     * @return A copy of the screen dimensions
     */
    public Dimension getScreenSize() {
        return new Dimension(screenSize); // Defensive copy
    }

    /**
     * Gets the scaling factor for this display configuration.
     * 
     * @return The scaling factor
     */
    public double getScalingFactor() {
        return scalingFactor;
    }

    /**
     * Returns a string representation of this display configuration for debugging.
     * 
     * @return A formatted string with all configuration details
     */
    @Override
    public String toString() {
        return String.format("DisplayConfiguration{dpi=%d, osName='%s', screenSize=%dx%d, scalingFactor=%.2f}",
                dpi, osName, screenSize.width, screenSize.height, scalingFactor);
    }

    /**
     * Checks if this configuration represents a high-DPI display.
     * 
     * @return true if DPI is above standard (96 DPI), false otherwise
     */
    public boolean isHighDpi() {
        return dpi > 96;
    }

    /**
     * Checks if this configuration is for a Windows operating system.
     * 
     * @return true if OS is Windows, false otherwise
     */
    public boolean isWindows() {
        return osName != null && osName.toLowerCase().contains("windows");
    }

    /**
     * Checks if this configuration is for a Linux operating system.
     * 
     * @return true if OS is Linux, false otherwise
     */
    public boolean isLinux() {
        return osName != null && osName.toLowerCase().contains("linux");
    }
}