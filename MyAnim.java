import java.awt.Polygon;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JPanel;

public class MyAnim extends JPanel implements ActionListener 
{
	private Polygon polyS; // האות S
    private Polygon polyZ; // האות Z
    private Timer timer; // הטיימר שיריץ את האנימציה
    private double angle = 0;   // זווית הסיבוב
    private double scale = 1.0; // גודל האות
    private boolean growing = true; // האם האות כרגע גדלה או קטנה
	
    public MyAnim() {     
        polyS = loadPolygonFromFile("S.txt");
        polyZ = loadPolygonFromFile("Z.txt");
        
        // הגדרת טיימר: ירוץ כל 50 מילישניות ויקרא לפעולת actionPerformed
        timer = new javax.swing.Timer(50, this);
        timer.start();
    }
    private Polygon loadPolygonFromFile(String fileName) {
        Polygon p = new Polygon(); 
        try {
            java.util.Scanner sc = new java.util.Scanner(new java.io.File(fileName));
            while (sc.hasNextInt()) { // כל עוד יש מספרים בקובץ
                int x = sc.nextInt(); // קרא מספר ראשון (X)
                int y = sc.nextInt(); // קרא מספר שני (Y)
                p.addPoint(x, y);     // שים נקודה בגרפיקה
            }
            sc.close(); // סימנו לקרוא, סוגרים את הקובץ
        } catch (Exception e) {
            // אם הקובץ לא נמצא או שיש בעיה, נראה הודעה
            System.out.println("בעיה בקריאת הקובץ: " + fileName);
        }
        return p; 
    }
    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        java.awt.Graphics2D g2d = (java.awt.Graphics2D) g;
        g2d.setColor(java.awt.Color.BLUE);
        g2d.translate(100, 150);
        g2d.rotate(angle);
        g2d.fillPolygon(polyS);
        g2d.setTransform(new java.awt.geom.AffineTransform());
        g2d.setColor(java.awt.Color.RED);
        
        g2d.translate(300, 150); 
        
        g2d.scale(scale, scale); 
        g2d.fillPolygon(polyZ);
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        // 1. נגדיל את הזווית ב-0.1 בכל תקתוק. זה מה שיוצר את תנועת הסיבוב.
        angle += 0.1; 
        
        // 2. נעדכן את הגודל (Scale). 
        // אם המשתנה growing הוא true - נגדיל. אם false - נקטין.
        if (growing) {
            scale += 0.05; 
        } else {
            scale -= 0.05;
        }
        
        // 3. בדיקת גבולות: אם האות נהייתה גדולה מדי (פי 2) או קטנה מדי (חצי), נהפוך כיוון.
        if (scale > 2.0 || scale < 0.5) {
            growing = !growing;
        }

        // 4. הפקודה הכי חשובה: repaint. 
        // היא אומרת למחשב: "הנתונים השתנו! לך תפעיל שוב את paintComponent ותצייר מחדש".
        repaint();
    }
    
    public static void main(String[] args) 
	{
     javax.swing.JFrame frame = new javax.swing.JFrame("Dancing Letters");
        
        // 2. יוצרים אובייקט מהמחלקה שלנו (כאן הכל מתחיל)
        MyAnim panel = new MyAnim();
        
        // 3. מוסיפים את הציור לתוך החלון
        frame.add(panel);
        
        // 4. קובעים גודל לחלון
        frame.setSize(600, 400);
        
        // 5. דואגים שהתוכנית תיסגר כשלוחצים על ה-X
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        
        // 6. מראים את החלון על המסך
        frame.setVisible(true);
    }
}
