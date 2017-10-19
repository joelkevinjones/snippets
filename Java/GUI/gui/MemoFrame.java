package gui;

import Model.Memo;
import Model.MemoModel;
import Model.MemoSource;

import util.Logging;

import com.google.common.base.Strings;
import com.palantir.ptoss.cinch.core.Bindable;
import com.palantir.ptoss.cinch.core.Bindings;
import com.palantir.ptoss.cinch.swing.Bound;
import com.palantir.ptoss.cinch.swing.OnClick;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import java.net.URL;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.border.EmptyBorder;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

@Bindable
/// An editing frame for a Memo
public class MemoFrame extends JFrame {
    /* General Format
     *
     * ===========================================================
     * ||  memo icon | title                                    ||
     * ===========================================================
     * attachment pop-up | "Title" | Title | lArrowBut | rArrBut
     *                     "Date"  | Date | "Time" | Time | #
     *        "Categories" | pop-up | pop-up              | of
     * time insert button | checkBox | "Private"          | #
     * memoTextEditBox
     * ===========================================================
     *
     *  o attachment pop-up & time insert button are at fixed offset
     *    from left edge
     *  o lArrowBut, rArrBut, and "# of #" are at fixed offset from
     *    right edge
     */
    HashMap<String, Action> actionMap;

    private final Bindings bindings = new Bindings();
    @Bindable
    private final MemoModel model = new MemoModel();

    // First row
    private JButton attachmentButton;
    private JPopupMenu attachmentPopup;
    private JLabel titleLabel;
    @Bound(to = "title")
    private JTextField titleField;
    @OnClick(call = "model.nextMemo")
    private JButton prevBut;
    @OnClick(call = "model.previousMemo")
    private JButton nextBut;

    // Second row
    private JLabel dateLabel;
    //@Bound(to = "creationDate")
    private JTextField dateField;
    private JLabel timeLabel;
    @Bound(to = "creationTime")
    private JTextField timeField;
    private JLabel countField;

    // Fifth row
    @Bound(to = "contents")
    private JTextArea contentsArea;

    private static final int SPRING_LAYOUT = 0;
    private static final int GRIDBAG_LAYOUT = 1;
    private final int layoutType = SPRING_LAYOUT;

    class DateVerifier extends InputVerifier {
        static final String LOZENGE = "\u25CA";
        static final String invalidDateString = LOZENGE + LOZENGE +
                                                " Invalid Date " +
                                                LOZENGE + LOZENGE;

        private MemoModel memoModel;
        private DateFormat df =
            new SimpleDateFormat("MMMMM dd, yyyy", Locale.ENGLISH);
        public DateVerifier(MemoModel memoModel) {
            this.memoModel = memoModel;
        }

        public boolean shouldYieldFocus(JComponent input) {
            JTextField tf = (JTextField) input;
            String inputString = tf.getText();
            if (Strings.isNullOrEmpty(inputString)) {
                // set output string to contents of model and return
                tf.setText(memoModel.getCreationDate());
                return true;
            }
            Date parsedDate = parseDate(inputString);
            if (parsedDate != null) {
                setCalendarFromDate(parsedDate);
                return true;
            }
            // input string is not a valid date, set to error string, but
            // don't change the model
            tf.setText(invalidDateString);
            return true;
        }

        public boolean verify(JComponent input) {
            return true;
        }

        private Date parseDate(String inputString) {
            ParsePosition parsePosition = new ParsePosition(0);
            Date date = df.parse(inputString, parsePosition);
            return date;
        }

        private void setCalendarFromDate(Date date) {
            memoModel.getCreationDateAsCal().setTime(date);
            memoModel.update();
        }
    }

    private void createComponents() {
        // TODO: make attachment button have themed icon/background
        // TODO: adjust textfield to have interior themed border
        // TODO: titleLabel smaller font
        // TODO: make buttons show disable appearence when not foreground app
        attachmentButton = makeButton(actionMap.get("attachItem"));
        attachmentPopup = new JPopupMenu();
        // TODO: add menu items to attachmentPopup
        attachmentPopup.setInvoker(attachmentButton);

        titleLabel = new JLabel("Title");

        titleField = new JTextField("memo title");
        Dimension titleFieldDim = new Dimension(421, 44);
        titleField.setMinimumSize(titleFieldDim);
        titleField.setPreferredSize(titleFieldDim);
        Insets titleFieldOrigInsets = titleField.getInsets();
        EmptyBorder emptyBorder = new EmptyBorder(titleFieldOrigInsets.top,0,0,0);
        titleField.setBorder(emptyBorder);

        prevBut = makeButton(actionMap.get("prevItem"));
        Insets prevButOrigInsets = prevBut.getInsets();
        emptyBorder = new EmptyBorder(prevButOrigInsets.top,0,0,0);
        prevBut.setBorder(emptyBorder);

        nextBut = makeButton(actionMap.get("nextItem"));
        nextBut.setBorder(emptyBorder);

        dateLabel = new JLabel("Date");
        dateLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dateLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        dateField = new JTextField("");
        dateField.setInputVerifier(new DateVerifier(model));

        timeLabel = new JLabel("Time");
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        timeLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        timeField = new JTextField("");

        contentsArea = new JTextArea();
    }

    // Assume all components in panel are laid out in a row, ordered left to
    // right.
    private /*static*/ void setPanelMinSize(JPanel panel) {
        int compCount = panel.getComponentCount();
        // TODO: check and throw exception if not SpringLayout
        SpringLayout layout = (SpringLayout) panel.getLayout();
        Spring width = Spring.constant(0);
        for (int i = 0; i < compCount; i++) {
            Component c = panel.getComponent(i);
            SpringLayout.Constraints constriants = layout.getConstraints(c);
            width = Spring.sum(constriants.getConstraint(SpringLayout.WIDTH),
                               width);
        }
    }

     /*
      * Use SpringLayout, with the following definitions and constraints:
      * AT - attach button   TL - title label      TT - title text field
      * PI - previous item button NI - next item button
      * DL - date label      DT - date text field  ML - time label
      * MT - time text field
      *
      * First row
      * AT.west at con.west
      * TL.west at AT.east
      * TT.west at TL.east
      * PI.east at NI.west
      * NI.east at con.east
      *
      * Second row
      * DL.north at TL.south
      * DL.east at TL.east
      * DT.west at DL.east
      * ML.west at DT.east
      * MT.west at ML.east
      */

    private JPanel layoutUsingSpring() {
        JPanel panel = new BackgroundPanel("SunstoneFullBack");
        BoxLayout boxLayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(boxLayout);

        // First row
        SpringLayout layout = new SpringLayout();
        JPanel row1 = new JPanel(layout);
        row1.setOpaque(false);
        row1.add(attachmentButton);
        row1.add(titleLabel);
        row1.add(titleField);
        row1.add(prevBut);
        row1.add(nextBut);
        panel.add(row1);


        // Add constraints between components

        // First row
        layout.putConstraint(SpringLayout.WEST, attachmentButton,
                             0,
                             SpringLayout.WEST, row1);
        layout.putConstraint(SpringLayout.NORTH, row1,
                             25,
                             SpringLayout.NORTH, attachmentButton);
        layout.putConstraint(SpringLayout.WEST, titleLabel,
                             15,
                             SpringLayout.EAST, attachmentButton);
        layout.putConstraint(SpringLayout.SOUTH, titleLabel,
                             0,
                             SpringLayout.SOUTH, attachmentButton);
        layout.putConstraint(SpringLayout.WEST, titleField,
                             0,
                             SpringLayout.EAST, titleLabel);
        layout.putConstraint(SpringLayout.SOUTH, titleField,
                             0,
                             SpringLayout.SOUTH, titleLabel);
        layout.putConstraint(SpringLayout.EAST, nextBut,
                             0,
                             SpringLayout.EAST, row1);
        layout.putConstraint(SpringLayout.EAST, prevBut,
                             0,
                             SpringLayout.WEST, nextBut);
        layout.putConstraint(SpringLayout.EAST, titleField,
                             -10,
                             SpringLayout.WEST, prevBut);
        layout.putConstraint(SpringLayout.NORTH, titleField,
                             10,
                             SpringLayout.NORTH, row1);

        // Second row
        layout = new SpringLayout();
        JPanel row2 = new JPanel(layout);
        row2.setOpaque(false);
        row2.add(dateLabel);
        row2.add(dateField);
        row2.add(timeLabel);
        row2.add(timeField);
        panel.add(row2);

        layout.putConstraint(SpringLayout.NORTH, row2,
                             0,
                             SpringLayout.NORTH, dateLabel);

        layout.putConstraint(SpringLayout.NORTH, dateLabel,
                             0,
                             SpringLayout.SOUTH, titleLabel);
        layout.putConstraint(SpringLayout.EAST, dateLabel,
                             0,
                             SpringLayout.EAST, titleLabel);
        layout.putConstraint(SpringLayout.SOUTH, dateLabel,
                             0,
                             SpringLayout.SOUTH, dateField);
        layout.putConstraint(SpringLayout.WEST, dateField,
                             0,
                             SpringLayout.EAST, dateLabel);
        layout.putConstraint(SpringLayout.WEST, timeLabel,
                             10,
                             SpringLayout.EAST, dateField);
        layout.putConstraint(SpringLayout.WEST, timeField,
                             0,
                             SpringLayout.EAST, timeLabel);
        layout.putConstraint(SpringLayout.EAST, timeField,
                             0,
                             SpringLayout.EAST, row2);


        // Row 5
        panel.add(contentsArea);

        // set panel minimum size
        // TODO disabled for now, as top level layout is SpringLayout
        //setPanelMinSize(panel);

        return panel;
    }

    private void layoutUsingGridBag(JPanel panel, GridBagLayout gridbag) {
        GridBagConstraints c = new GridBagConstraints();
        //JPanel panel = new BackgroundPanel(gridbag, "SunstoneFullBack");
        panel.setLayout(gridbag);

        c.gridx = 0;
        c.gridy = 0;

        // attach icon, title, prev/next arrows
        c.gridx = 1;
        int prevAnchor = c.anchor;
        c.anchor = GridBagConstraints.FIRST_LINE_START;
        double prevWeightx = c.weightx;
        c.weightx = 1.0;
        panel.add(attachmentButton, c);
        c.anchor = prevAnchor;
        c.weightx = prevWeightx;

        c.gridx = 2;
        prevWeightx = c.weightx;
        c.weightx = 1.0;
        panel.add(titleLabel, c);
        c.weightx = prevWeightx;

        c.gridx = 3;
        int prevFill = c.fill;
        int prevGridWidth = c.gridwidth;
        prevWeightx = c.weightx;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        c.weightx = 0.5;
        panel.add(titleField, c);
        c.fill = prevFill;
        c.gridwidth = prevGridWidth;
        c.weightx = prevWeightx;

        c.gridx = 5;
        panel.add(prevBut, c);

        c.gridx = 6;
        JButton nextBut = makeButton(actionMap.get("nextItem"));
        panel.add(nextBut, c);

        // date, time
        c.gridx = 1;
        c.gridy = 2;

        panel.add(dateLabel, c);

        c.gridx = 3;
        panel.add(dateField, c);

        c.gridx = 4;
        panel.add(timeLabel, c);

        c.gridx = 5;
        panel.add(timeField, c);
    }

    private void constrainFrameSize() {
        // For now, hard wire the minimum frame size.  Later we'll calculate it, to handle
        // internationalization, changing text sizes. etc.
        Dimension frameMinD = new Dimension(475, 120);
        this.setMinimumSize(frameMinD);

        // disable shrinking too far
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                Dimension d = MemoFrame.this.getSize();
                Dimension minD = MemoFrame.this.getMinimumSize();
                if (d.width < minD.width)
                    d.width = minD.width;
                if (d.height < minD.height)
                    d.height = minD.height;
                MemoFrame.this.setSize(d);
            }
        });
    }

    private void layoutComponents() {
        // Layout components
        Container container = getContentPane();
        JPanel panel;
        if (layoutType == GRIDBAG_LAYOUT) {
            GridBagLayout gridbag = new GridBagLayout();
            panel = new BackgroundPanel(gridbag, "SunstoneFullBack");
            panel.setLayout(gridbag);

            layoutUsingGridBag(panel, gridbag);
        }
        else if (layoutType == SPRING_LAYOUT) {
//             SpringLayout springLayout = new SpringLayout();
//             panel = new BackgroundPanel(springLayout, "SunstoneFullBack");
//             layoutUsingSpring(panel, springLayout);
            panel = layoutUsingSpring();
        }

        container.add(panel);
    }

    public MemoFrame(HashMap<String, Action> actionMap) {
        // TODO: memo icon in title bar
        // TODO: add real contents from memo

        super("memo title");
        this.actionMap = actionMap;

        createComponents();

        bindings.bind(this);

        layoutComponents();

        constrainFrameSize();
    }

    // TODO: refactor with ButtonBar.makeIcon
    protected static ImageIcon makeIcon(String imageName, String altText) {
        //Look for the image.
        // TODO: fix to use system specific file seperator, not "/"?
        String imgLocation = "images/" + imageName + ".gif";
        URL imageURL = ButtonBar.class.getResource(imgLocation);
        if (imageURL == null) {
            System.err.println("Couldn't find " + imgLocation);
        }
        return new ImageIcon(imageURL, altText);
    }

    // TODO: refactor with ButtonBar.makeButton
    protected static JButton makeButton(Action action) {
        JButton button = new JButton(action);

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        MemoFrame frame = new MemoFrame(MenuBar.createActions());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Logging.initialize();
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
            UIManager.put("swing.boldMetal", Boolean.FALSE);
               createAndShowGUI();
            }
        });
    }
}
