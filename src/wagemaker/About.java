package wagemaker;

/******************************************************************************************************
 * @author by Ricardo Wagemaker (["java"] + "@" + "wagemaker.co.uk") 2013-2015
 * @version 1.5.6
 * @since   2013 - 2025
 ******************************************************************************************************/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class About extends JDialog {
    private static final long serialVersionUID = 1L;
    private Color themeColor;
    private Color backgroundColor;
    private Color textColor;
    
    public About(JFrame parent) {
        super(parent, "About " + CalcProperties.Title, true);
        initializeColors();
        initializeComponents();
        setupWindow();
    }
    
    private void initializeColors() {
        try {
            // Get the current theme color dynamically
            themeColor = CalcProperties.Theme_ORANGE();
            if (themeColor == null) {
                themeColor = CalcProperties.ORANGE; // fallback
            }
            
            // Create a darker version for background
            backgroundColor = new Color(
                Math.max(0, themeColor.getRed() - 40),
                Math.max(0, themeColor.getGreen() - 40),
                Math.max(0, themeColor.getBlue() - 40)
            );
            
            textColor = CalcProperties.WHITE;
        } catch (IOException e) {
            // Fallback colors
            themeColor = CalcProperties.ORANGE;
            backgroundColor = new Color(215, 65, 0);
            textColor = CalcProperties.WHITE;
        }
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create gradient from theme color to darker version
                GradientPaint gradient = new GradientPaint(
                    0, 0, themeColor,
                    0, getHeight(), backgroundColor
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        
        // Header section with logo and title
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Version info
        JPanel versionPanel = createVersionPanel();
        mainPanel.add(versionPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        
        // Developer info
        JPanel developerPanel = createDeveloperPanel();
        mainPanel.add(developerPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        
        // Description
        JPanel descriptionPanel = createDescriptionPanel();
        mainPanel.add(descriptionPanel);
        mainPanel.add(Box.createVerticalStrut(30));
        
        // Features list
        JPanel featuresPanel = createFeaturesPanel();
        mainPanel.add(featuresPanel);
        mainPanel.add(Box.createVerticalGlue());
        
        // Close button
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        
        // App icon
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/gcclinux.png"));
            Image scaledImage = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
            panel.add(iconLabel);
            panel.add(Box.createHorizontalStrut(15));
        } catch (Exception e) {
            // Icon not found, continue without it
        }
        
        // Title
        JLabel titleLabel = new JLabel(CalcProperties.Title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(textColor);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel);
        
        return panel;
    }
    
    private JPanel createVersionPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        
        JLabel versionLabel = new JLabel("Version " + CalcProperties.Version);
        versionLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
        versionLabel.setForeground(textColor);
        panel.add(versionLabel);
        
        return panel;
    }
    
    private JPanel createDeveloperPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel createdByLabel = new JLabel("Created by");
        createdByLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        createdByLabel.setForeground(textColor);
        createdByLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel developerLabel = new JLabel(CalcProperties.Developer);
        developerLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        developerLabel.setForeground(textColor);
        developerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(createdByLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(developerLabel);
        
        return panel;
    }
    
    private JPanel createDescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        
        JTextArea descArea = new JTextArea(
            "A modern, multi-theme calculator inspired by Linux design principles. " +
            "Features vibrant color themes, full keyboard support, audio feedback, " +
            "calculation history, and a clean, intuitive interface designed for productivity."
        );
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descArea.setForeground(textColor);
        descArea.setOpaque(false);
        descArea.setEditable(false);
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        panel.add(descArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createFeaturesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        
        JLabel featuresTitle = new JLabel("Key Features:");
        featuresTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        featuresTitle.setForeground(textColor);
        featuresTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(featuresTitle);
        panel.add(Box.createVerticalStrut(10));
        
        String[] features = {
            "• Multiple Color Themes (Orange, Purple, Lime, Plain, Black)",
            "• Full Keyboard Support",
            "• Calculation History",
            "• Audio Feedback (Optional)",
            "• Export to Excel",
            "• Customizable Button Styles",
            "• Cross-Platform Compatibility"
        };
        
        for (String feature : features) {
            JLabel featureLabel = new JLabel(feature);
            featureLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            featureLabel.setForeground(textColor);
            featureLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(featureLabel);
            panel.add(Box.createVerticalStrut(3));
        }
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        closeButton.setPreferredSize(new Dimension(100, 35));
        closeButton.setBackground(textColor);
        closeButton.setForeground(themeColor);
        closeButton.setBorder(BorderFactory.createRaisedBevelBorder());
        closeButton.setFocusPainted(false);
        
        // Hover effect
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(new Color(240, 240, 240));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                closeButton.setBackground(textColor);
            }
        });
        
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panel.add(closeButton);
        
        return panel;
    }
    
    private void setupWindow() {
        setSize(450, 660);
        setResizable(false);
        setLocationRelativeTo(getParent());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Set window icon
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/images/gcclinux.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // Icon not found, continue without it
        }
        
        // Close on Escape key
        getRootPane().registerKeyboardAction(
            e -> dispose(),
            KeyStroke.getKeyStroke("ESCAPE"),
            JComponent.WHEN_IN_FOCUSED_WINDOW
        );
        
        // Handle window closing
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }
    
    // Static method to show the About dialog
    public static void showAbout(JFrame parent) {
        SwingUtilities.invokeLater(() -> {
            About aboutDialog = new About(parent);
            aboutDialog.setVisible(true);
        });
    }
    
    // Legacy main method for backward compatibility
    @SuppressWarnings({ "static-access" })
    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            try {
                CalcProperties.setDesign();
            } catch (Exception e) {
                // Use default look and feel
            }
            
            JFrame dummyFrame = new JFrame();
            dummyFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            showAbout(dummyFrame);
        });
    }
}