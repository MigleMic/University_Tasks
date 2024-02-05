import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class App
{
    static Scanner scanner = new Scanner(System.in);

    public class Customer
    {
        String Id;
        String Ak;
        String Fname;
        String Lname;
        String Phone;
        String Email;
        String City;
        String Adress;

    public Customer(String id, String ak, String fname, String lname, String phone, String email, String city, String adress)
    {
        Id = id;
        Ak = ak;
        Fname = fname;
        Lname = lname;
        Phone = phone;
        Email = email;
        City = city;
        Adress = adress;
    }
    }

    public class Product
    {
        String Id;
        String Name;
        Float Price;
        String CompanyCode;

        public Product(String id, String name, Float price, String companyCode)
        {
            Id = id;
            Name = name;
            Price = price;
            CompanyCode = companyCode;
        }
    }


    //FIRMA2 PREKES5 UZSAKYMAI3 PIRKEJAS3
    public enum Actions
    {
        VIEW_PRODUCTS,              
        VIEW_COMPANIES,     
        VIEW_CUSTOMERS,     
        VIEW_ORDERS,  
        VIEW_COMPANIES_AND_PRODUCTS,  
        SEARCH_COMPANY,   
        SEARCH_ORDER,  
        REGISTER_CUSTOMER,  
        REGISTER_PRODUCT,  
        EDIT_CUSTOMER_INFO, 
        EDIT_PRODUCT_INFO,          
        MERGE_COMPANIES, 
        REMOVE_ORDER,
        EXIT     
    }

    public static void loadDriver()
    {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException cnfe) {
            System.out.println("Nerasta Driver klasė!");
            cnfe.printStackTrace();
            System.exit(1);
        }
    }

    public static Connection getConnection()
    {
        Connection connection = null;
        try
        {
            connection = DriverManager.getConnection("jdbc:postgresql://pgsql2.mif/studentu", "mimi7678", "WeiYing2021/");
        }
        catch (SQLException e)
        {
            System.out.println("Negalima prisijungti prie duomenų bazės");
            e.printStackTrace();
            return null;
        }
        System.out.println("Sėkmingai prisijungta prie duomen bazės!");

        return connection;
    }

    static Actions showMenu()
    {
        System.out.println("Įveskite 0 jei norite peržiūrėti visas prekes");
        System.out.println("Įveskite 1 jei norite peržiūrėti visas firmas");
        System.out.println("Įveskite 2 jei norite peržiūrėti visus klientus");
        System.out.println("Įveskite 3 jei norite peržiūrėti visus užsakymus");
        System.out.println("Įveskite 4 jei norite pamatyti firmas kartu su ju tiekiamais baldais");
        System.out.println("Įveskite 5 jei norite ieškoti firmos pagal pavadinimą");
        System.out.println("Įveskite 6 jei norite ieškoti užsakymo pagal jo numerį");
        System.out.println("Įveskite 7 jei norite užregistruoti naują klientą");
        System.out.println("Įveskite 8 jei norite užregistruoti naują prekę");
        System.out.println("Įveskite 9 jei norite atnaujinti kliento adresą");
        System.out.println("Įveskite 10 jei norite atnaujinti prekės kainą");
        System.out.println("Įveskite 11 jei norite sujungti dvi firmas");
        System.out.println("Įveskite 12 jei norite ištrinti užsakymą"); 
        System.out.println("Įveskite 13 jei norite išeiti"); 

        int choice = checkChoice("[0-9]{0,2}");
        return choice <= Actions.values().length && choice >= 0 ? Actions.values()[choice] : showMenu();
    }
    public static void main(String[] args)
    {
        App app = new App();
        app.start();
    }

    public void start()
    {
        loadDriver();
        Connection connection = getConnection();

        boolean keepAsking = true;

        while(keepAsking)
        {
            switch(showMenu())
            {
                case VIEW_PRODUCTS:
                    viewProducts(connection);
                    break;
                case VIEW_COMPANIES:
                    viewCompanies(connection);
                    break;
                case VIEW_CUSTOMERS:
                    viewCustomers(connection);
                    break;
                case VIEW_ORDERS:
                    viewOrders(connection);
                    break;
                case REGISTER_CUSTOMER: 
                    registerCustomer(connection);
                    break;
                case REGISTER_PRODUCT:  
                    registerProduct(connection);
                    break;
                case EDIT_CUSTOMER_INFO:    
                    viewCustomers(connection);
                    updateCustomerAdress(connection, askCustomer(), askAddress());
                    break;
                case EDIT_PRODUCT_INFO:     
                    viewProducts(connection);
                    updateProductPrice(connection, askProductId(), askPrice());
                    break;
                case SEARCH_COMPANY:    
                    searchCompanyName(connection, askCompany());
                    break;
                case SEARCH_ORDER:      
                    searchOrder(connection, askOrderId());
                    break;
                case VIEW_COMPANIES_AND_PRODUCTS:     
                    showCompaniesAndProducts(connection);
                    break;
                case MERGE_COMPANIES:     
                    mergeCompanies(connection);
                    break;
                case REMOVE_ORDER:    
                    deleteOrder(connection, askOrderId());
                    break;
                case EXIT:
                    System.out.println("Baigiamas darbas...");
                    keepAsking = false;
                    break;
            }
        }

        if( connection != null)
        {
            try
            {
                connection.close();
            }
            catch(SQLException e)
            {
                System.out.println("Negalima nutraukti ryšio!");
                e.printStackTrace();
            }
        }
    }
    

    public void viewProducts(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }

        Statement statement = null;
        ResultSet result = null;
        String format = "%-30s";
        try
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM mimi7678.Preke");

            System.out.printf(format,"Prekes_kodas");
            System.out.printf(format, "Baldo_tipas");
            System.out.printf(format,"Kaina");
            System.out.printf(format,"Imones_kodas");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format, result.getString("Prekes_kodas"));
                System.out.printf(format, result.getString("Baldo_tipas"));
                System.out.printf(format, result.getFloat("Kaina"));
                System.out.printf(format, result.getString("Imones_kodas"));
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(result != null)
                    result.close();
                if(statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public void viewCompanies(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        ResultSet result = null;
        String format = "%-30s";
        try
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM mimi7678.Firma");
            System.out.printf(format,"Imonės kodas");
            System.out.printf(format,"Pavadinimas");
            System.out.printf(format,"Miestas");
            System.out.printf(format,"Gatve");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format,result.getString("Imones_kodas"));
                System.out.printf(format,result.getString("Pavadinimas"));
                System.out.printf(format,result.getString("Miestas"));
                System.out.printf(format, result.getString("Gatve"));
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(result != null)
                    result.close();
                if(statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public void viewCustomers(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }

        Statement statement = null;
        ResultSet result = null;
        String format = "%-23s";

        try
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM mimi7678.Pirkejas");
            System.out.printf(format, "Pirkejo_kodas");
            System.out.printf(format, "Asmens_kodas");
            System.out.printf(format, "Vardas");
            System.out.printf(format, "Pavarde");
            System.out.printf(format, "Telefonas");
            System.out.printf(format, "El_Pastas");
            System.out.printf(format, "Miestas");
            System.out.printf(format, "Adresas");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format,result.getString("Pirkejo_kodas"));
                System.out.printf(format,result.getString("Asmens_kodas"));
                System.out.printf(format,result.getString("Vardas"));
                System.out.printf(format,result.getString("Pavarde"));
                System.out.printf(format,result.getString("Telefonas"));
                
                if (result.getString("El_Pastas") == null)
                    System.out.printf(format,"Nezinomas");
                else
                    System.out.printf(format,result.getString("El_Pastas")); 
                System.out.printf(format,result.getString("Miestas")); 
                System.out.printf(format,result.getString("Adresas"));      
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(result != null)
                    result.close();
                if(statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public void viewOrders(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }

        Statement statement = null;
        ResultSet result = null;
        String format = "%-30s";
        try
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT * FROM mimi7678.Uzsakymai");
            System.out.printf(format, "Uzsakymo_numeris");
            System.out.printf(format, "Uzsisakymo_data");
            System.out.printf(format, "Gavimo_data");
            System.out.printf(format, "Preke");
            System.out.printf(format, "Kiekis");
            System.out.printf(format, "Pirkejo_kodas");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format,result.getInt("Uzsakymo_numeris"));
                System.out.printf(format,result.getDate("Uzsisakymo_data"));
                System.out.printf(format,result.getDate("Gavimo_data"));
                System.out.printf(format,result.getString("Preke"));
                System.out.printf(format,result.getInt("Kiekis"));
                System.out.printf(format,result.getString("Pirkejas"));
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(result != null)
                    result.close();
                if(statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public void registerCustomer(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Should be impossible to get here with null");
            return;
        }

        Customer customer = getCustomer();
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO mimi7678.Pirkejas(Pirkejo_kodas, Asmens_kodas, Vardas, Pavarde, Telefonas, El_Pastas, Miestas, Adresas)" + 
            "VALUES ('" + customer.Id + "','" + customer.Ak + "','" + customer.Fname + "','" +
            customer.Lname +  "','" + customer.Phone + "','" + 
            customer.Email + "','" + customer.City + "','" + customer.Adress + "')");
            System.out.println("Sėkmingai užregistruotas naujas klientas!\n");
        }
        catch (SQLException e)
        {
            System.out.println("SQL ERROR");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Unexpected SQL error");
                e.printStackTrace();
            }
        }
    }

    public Customer getCustomer()
    {
        System.out.print("Įveskite kLiento kodą :");
        String id = checkPattern("[0-9]{10}");
        System.out.print("Įveskite asmens kodą: ");
        String ak = checkPattern("[0-9]{11}");
        System.out.print("Įveskite vardą: ");
        String fname = checkPattern("[a-zA-Z]{2,14}");
        System.out.print("Įveskite pavardę: ");
        String lname = checkPattern("[a-zA-Z]{2,20}");
        System.out.print("Įveskite telefono numerį: ");
        String phone = checkPattern("[0-9]{9}");
        System.out.print("Įveskite elektroninį paštą: ");
        String email = checkPattern("^(.+)@(.+)$");
        System.out.print("Įveskite miestą: ");
        String city = checkPattern("[a-zA-Z]{2,20}");
        System.out.print("Įveskite adresą: ");
        String adress = checkPattern("[a-zA-Z]{5,20}");

        return new Customer(id,ak,fname,lname,phone,email,city,adress);
    }

    public void registerProduct(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }

        ShowAllCompanies(connection);
        Product product = getProduct();
        Statement statement = null;
        try
        {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO mimi7678.Preke(Prekes_kodas, Baldo_tipas, Kaina, Imones_kodas)" + 
            "VALUES ('" + product.Id + "','" + product.Name + "','" + product.Price + "','" +
            product.CompanyCode +  "')");
            System.out.println("Sėkmingai užregistruota nauja prekė!\n");
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public void ShowAllCompanies(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        ResultSet result = null;
        String format = "%-30s";
        try
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT Imones_kodas, Pavadinimas FROM mimi7678.Firma");
            System.out.println("Visos kol kas užregistruotos įmonės: \n");
            System.out.printf(format,"Įmonės kodas");
            System.out.printf(format,"Pavadinimas");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format,result.getString("Imones_kodas"));
                System.out.printf(format,result.getString("Pavadinimas"));
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(result != null)
                    result.close();
                if(statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public Product getProduct()
    {
        System.out.print("Įveskite prekės kodą:");
        String id = checkPattern("[0-9]{10}");
        System.out.print("Įveskite baldo tipą: ");
        String name = checkPattern("[a-zA-Z]{3,20}");
        System.out.print("Įveskite kainą: ");
        String price = checkPattern("([0-9]*[.]?[0-9]){2,6}");
        float fprice = Float.parseFloat(price);
        System.out.print("Įveskite įmonės kodą iš turimų įmonių: ");
        String companyCode = checkPattern("[0-9]{10}");
        return new Product(id, name, fprice, companyCode);
    }

    public void updateCustomerAdress(Connection connection, String customerId, String adress)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        try 
        {
            viewCustomers(connection);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE mimi7678.Pirkejas SET Adresas = '" + adress + "' WHERE Pirkejo_kodas = '" + customerId + "'");
            System.out.println("Sėkmingai atnaujintas kliento adresas!\n");
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public String askCustomer()
    {
        System.out.print("Įveskite kliento kodą, kurio adresą norite atnaujinti:");
        String id = checkPattern("[0-9]{10}");
        return id;
    }

    public String askAddress()
    {
        System.out.print("Įveskite naują adresą:");
        String adress = checkPattern("[a-zA-Z]{5,20}");
        return adress;
    }

    public void updateProductPrice(Connection connection, String productId, Float price)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        try 
        {
            viewProducts(connection);
            statement = connection.createStatement();
            statement.executeUpdate("UPDATE mimi7678.Preke SET Kaina = '" + price + "' WHERE Prekes_kodas = '" + productId + "'");
            System.out.println("Sėkmingai atnaujinta prekės kaina!\n");
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public String askProductId()
    {
        System.out.print("Įveskite prekės kodą, kurios kainą norite atnaujinti:");
        String id = checkPattern("[0-9]{10}");
        return id;
    }

    public Float askPrice()
    {
        System.out.print("Įveskite nauja kainą:");
        String price = checkPattern("([0-9]*[.]?[0-9]){2,6}");
        float fprice = Float.parseFloat(price);
        return fprice;
    }

    public void searchCompanyName(Connection connection, String name)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        ResultSet result = null;
        String format = "%-22s";
        
        try 
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT Imones_kodas, Pavadinimas, Miestas, Gatve from mimi7678.Firma WHERE Pavadinimas LIKE '%" + name + "%'");
            System.out.printf(format,"\nImonės kodas");
            System.out.printf(format,"Pavadinimas");
            System.out.printf(format,"Miestas");
            System.out.printf(format,"Gatve");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format,result.getString("Imones_kodas"));
                System.out.printf(format,result.getString("Pavadinimas"));
                System.out.printf(format,result.getString("Miestas"));
                System.out.printf(format,result.getString("Gatve"));
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public String askCompany()
    {
        System.out.print("Įveskite firmos pavadinimą:");
        String name = checkPattern("[a-zA-Z]{2,20}");
        return name;
    }

    public void searchOrder(Connection connection, String orderId)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        ResultSet result = null;
        String format = "%-22s";
        
        try 
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT Uzsakymo_numeris, Uzsisakymo_data, Gavimo_data, Preke, Kiekis, Pirkejas from mimi7678.Uzsakymai WHERE Uzsakymo_numeris = '" + orderId + "'");
            System.out.printf(format,"\nUzsakymo_numeris");
            System.out.printf(format,"Uzsisakymo_data");
            System.out.printf(format,"Gavimo_data");
            System.out.printf(format,"Preke");
            System.out.printf(format,"Kiekis");
            System.out.printf(format,"Pirkejas");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format,result.getString("Uzsakymo_numeris"));
                System.out.printf(format,result.getString("Uzsisakymo_data"));
                System.out.printf(format,result.getString("Gavimo_data"));
                System.out.printf(format,result.getString("Preke"));
                System.out.printf(format,result.getString("Kiekis"));
                System.out.printf(format,result.getString("Pirkejas"));
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public String askOrderId()
    {
        System.out.print("Įveskite užsakymo numerį: ");
        String id = checkPattern("[0-9]{1,3}");
        return id;
    }

    public void showCompaniesAndProducts(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        ResultSet result = null;
        String format = "%-22s";
        
        try 
        {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT Firma.Imones_kodas, Firma.Pavadinimas, Preke.Prekes_kodas, Preke.Baldo_tipas FROM mimi7678.Firma, mimi7678.Preke WHERE Firma.Imones_kodas = Preke.Imones_kodas ORDER BY Firma.Pavadinimas");
            
            
            System.out.printf(format,"\nImones_kodas");
            System.out.printf(format,"Pavadinimas");
            System.out.printf(format,"Prekes_kodas");
            System.out.printf(format,"Baldo_tipas");
            System.out.println("\n");
            while(result.next())
            {
                System.out.printf(format,result.getString("Imones_kodas"));
                System.out.printf(format,result.getString("Pavadinimas"));
                System.out.printf(format,result.getString("Prekes_kodas"));
                System.out.printf(format,result.getString("Baldo_tipas"));
                System.out.println("\n");
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public void mergeCompanies(Connection connection)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        try 
        {
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            ShowAllCompanies(connection);
            System.out.print("Įveskite firmos numerį, kurią norite sujungti su kita įmone (ši firma perims prekes): ");
            String firstId = checkPattern("[0-9]{10}");
            System.out.print("Įveskite kitos firmos numerį (ši firma bus pašalinta): ");
            String secondId = checkPattern("[0-9]{10}");

            statement.executeUpdate("UPDATE mimi7678.preke SET imones_kodas = '" + firstId + "' WHERE imones_kodas = '" + secondId + "'");
            
            statement.executeUpdate("DELETE FROM mimi7678.Firma WHERE Imones_kodas = '" + secondId + "'");
            connection.commit();
            connection.setAutoCommit(true); 
            System.out.println("Sėkmingai sujungts firmos!\n");
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace(); 
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public void deleteOrder(Connection connection, String orderId)
    {
        if(connection == null)
        {
            System.out.println("Neįmanoma patekti su null reikšme");
            return;
        }
        Statement statement = null;
        ResultSet result = null;
        try 
        {
            viewOrders(connection);
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM mimi7678.Uzsakymai WHERE Uzsakymo_numeris = '" + orderId + "'");
            System.out.println("Sėkmingai ištrintas užsakymas!\n");
        }
        catch (SQLException e)
        {
            System.out.println("SQL KLAIDA");
            e.printStackTrace(); 
        }
        finally
        {
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println("Netikėta SQL klaida");
                e.printStackTrace();
            }
        }
    }

    public String checkPattern(String pattern)
    {
        String result = "tikrinimas";

        int index = 0;
        Scanner scan = new Scanner(System.in);
        while(index == 0)
        {
            if(scan.hasNext(pattern))
            {
                index = 1;
                result = scan.nextLine();
            }
            else
            {
                System.out.println("Bandykite dar karta, netinkamas formatas!\n");
                scan.nextLine();
            }
        }

        return result;
    }

    public static int checkChoice(String choice)
    {
        int result = 100;

        int index = 0;
        Scanner scan = new Scanner(System.in);
        while(index == 0)
        {
            if(scan.hasNext(choice))
            {
                index = 1;
                result = scan.nextInt();
            }
            else
            {
                System.out.println("Bandykite dar karta, netinkamas formatas!\n");
                scan.nextLine();
            }
        }

        return result;
    }
}
