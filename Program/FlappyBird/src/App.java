import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    public static void main(String[] args){
        JFrame frame = new JFrame("Flappy Bird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 640);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        // Panel menu awal
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(Color.WHITE);

        JButton startButton = new JButton("Mulai");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBounds(130, 270, 100, 40); // letakkan tombol kecil di tengah

        menuPanel.add(startButton);

        frame.add(menuPanel);
        frame.setVisible(true);

        // Event saat tombol diklik
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll(); // hapus menu
                frame.repaint();

                JLabel scoreLabel = new JLabel("Score: 0");
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
                scoreLabel.setForeground(Color.BLACK);
                scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

                frame.add(scoreLabel, BorderLayout.NORTH); // tampilkan skor di atas
                FlappyBird flappyBird = new FlappyBird(scoreLabel);
                frame.add(flappyBird);

                frame.pack();
                flappyBird.requestFocus();
                frame.revalidate();
            }
        });
    }
}