package LoanCalc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GUI extends JFrame {

    private JTextField jtInvestmentAmmount;
    private JTextField jtNumberOfYears;
    private JTextField jtInterestRate;
    private JTextField jtNumberOfMonths;

    JRadioButton annual;
    JRadioButton linear;

    private JButton jbtDisplay;
    private JButton jbtGraph;

    public GUI() {

        setTitle("Būsto paskolos skaičiuoklė");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 3, 5, 5));

        JLabel jlInvestmentAmmount = new JLabel("Paskolos suma");
        JLabel jlNumberOfYears = new JLabel("Paskolos terminas(m)");
        JLabel jlNumberOfMonths = new JLabel("Paskolos terminas(men.)");
        JLabel jlInterestRate = new JLabel("Metinis procentas");

        jtInvestmentAmmount = new JTextField();
        jtNumberOfYears = new JTextField();
        jtNumberOfMonths = new JTextField();
        jtInterestRate = new JTextField();

        annual = new JRadioButton("Anuiteto");
        linear = new JRadioButton("Linijinis");

        jbtDisplay = new JButton("Ataskaita");
        jbtGraph = new JButton("Grafikas");

        add(jlInvestmentAmmount);
        add(jtInvestmentAmmount);
        add(jlNumberOfYears);
        add(jtNumberOfYears);
        add(jlNumberOfMonths);
        add(jtNumberOfMonths);
        ButtonGroup group = new ButtonGroup();
        group.add(annual);
        group.add(linear);
        add(annual);
        add(linear);

        add(jlInterestRate);
        add(jtInterestRate);

        add(jbtDisplay);
        add(jbtGraph);

        ListenerClass listener = new ListenerClass();
        annual.addActionListener(listener);
        linear.addActionListener(listener);
        jbtDisplay.addActionListener(listener);
        jbtGraph.addActionListener(listener);

        setVisible(true);
    }

    private class ListenerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (annual.isSelected()) {
                MortgageCalculator annually = new MortgageCalculator();
                annually.annualCalc();
                if (e.getSource() == jbtDisplay) {
                    try {
                        annually.displayAnnual();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
            if (linear.isSelected()) {
                MortgageCalculator linear = new MortgageCalculator();
                linear.linearCalc();
                if (e.getSource() == jbtDisplay) {
                    try {
                        linear.displayLinear();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        new GUI();
    }

    public class MortgageCalculator {
        private int years;
        private double loan;
        private double interestRate;
        private int months;
        private double Payment;
        private double totalPayment;
        private double interestPaid;
        private double monthlyPayment;
        private double loanBalance;
        private double balancePrinciple;
        private int displayCount;
        private String str;

        public void annualCalc() {
            this.years = Integer.parseInt(jtNumberOfYears.getText());
            this.loan = Double.parseDouble(jtInvestmentAmmount.getText());
            this.interestRate = Double.parseDouble(jtInterestRate.getText());
            this.months = Integer.parseInt(jtNumberOfMonths.getText());

            Payment = loan * (interestRate / 1200) * (1 + 1 / (Math.pow(1 + interestRate / (1200), years * 12) - 1));
            totalPayment = Payment * (years * 12 + months);
            loanBalance = totalPayment;
            balancePrinciple = this.loan;
        }

        public void displayAnnual() throws IOException {
            JFrame anuitet = new JFrame();
            JTextArea area;

            File myFile = new File("ataskaita_anuitetas.txt");
            myFile.delete();
            FileWriter myWriter = new FileWriter("ataskaita_anuitetas.txt");

            str = (String.format("%10s   %7s   %7s   %7s   %10s", "Menuo", "Suma", "  Palūkanos", "Įmoka", "Likutis"));
            System.out.println(str); //Pasitikrinimas
            myWriter.write(str);
            myWriter.write("\n");
            area = new JTextArea(str);
            area.append("\n");
            area.setBounds(0, 0, 400, 700);

            JScrollPane pane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            anuitet.setSize(400, 700);

            while ((displayCount < years * 12 + months)) {
                interestPaid = balancePrinciple * interestRate / 1200; //menesio ismoka
                loanBalance = loanBalance - Payment;
                balancePrinciple = balancePrinciple - (Payment - interestPaid);
                monthlyPayment = Payment - interestPaid;
                str = (String.format("%10d   %10.2f€   %10.2f€   %10.2f€   %10.2f€", displayCount + 1, monthlyPayment, interestPaid, Payment, loanBalance));
                myWriter.write(str);
                myWriter.write("\n");
                area.append(str);
                area.append(("\n"));

                System.out.println(str);
                displayCount++;

                area.setEditable(false);
                anuitet.add(pane);
                anuitet.setVisible(true);
            }
            myWriter.close();
        }

        public void linearCalc() {
            this.years = Integer.parseInt(jtNumberOfYears.getText());
            this.loan = Double.parseDouble(jtInvestmentAmmount.getText());
            this.interestRate = Double.parseDouble(jtInterestRate.getText());
            this.months = Integer.parseInt(jtNumberOfMonths.getText());
            monthlyPayment = loan / (years * 12);
            totalPayment = monthlyPayment * (years * 12 + months);
            loanBalance = totalPayment;
            balancePrinciple = this.loan;
        }

        public void displayLinear() throws IOException {

            JFrame linij = new JFrame();
            JTextArea area;

            File myFile = new File("ataskaita_linijinis.txt");
            myFile.delete();
            FileWriter myWriter = new FileWriter("ataskaita_linijinis.txt");

            str = (String.format("%10s   %7s   %7s   %7s   %10s", "Menuo", "Suma", "  Palūkanos", "Įmoka", "Likutis"));
            System.out.println(str);

            myWriter.write(str);
            myWriter.write("\n");

            area = new JTextArea(str);
            area.append("\n");
            area.setBounds(0, 0, 400, 700);

            JScrollPane pane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

            linij.setSize(400, 700);

            while ((displayCount < years * 12 + months)) {
                interestPaid = balancePrinciple * interestRate / 1200; //menesio ismoka
                loanBalance = loanBalance - monthlyPayment;
                balancePrinciple = balancePrinciple - (monthlyPayment - interestPaid);
                Payment = monthlyPayment + interestPaid;
                str = (String.format("%10d   %10.2f€    %10.2f€    %10.2f€   %10.2f€", displayCount + 1, monthlyPayment, interestPaid, Payment, loanBalance));

                area.append(str);
                area.append(("\n"));

                myWriter.write(str);
                myWriter.write("\n");

                System.out.println(str);
                displayCount++;

                area.setEditable(false);
                linij.add(pane);
                linij.setVisible(true);
            }
            myWriter.close();
        }
    }
}



