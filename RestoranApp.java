import java.util.ArrayList;
import java.util.Scanner;

public class RestoranApp {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<MenuItem> menu = new ArrayList<>();

        menu.add(new MenuItem("Nasi Goreng", 15000, "Makanan"));
        menu.add(new MenuItem("Rendang Sapi", 30000, "Makanan"));
        menu.add(new MenuItem("Sop Buntut", 25000, "Makanan"));
        menu.add(new MenuItem("Soto Banjar", 20000, "Makanan"));
        menu.add(new MenuItem("Spaghetti Bolognese", 35000, "Makanan"));
        menu.add(new MenuItem("Margherita Pizza", 40000, "Makanan"));
        menu.add(new MenuItem("Caesar Salad", 25000, "Makanan"));
        menu.add(new MenuItem("Tiramisu", 30000, "Makanan"));
        menu.add(new MenuItem("Sushi Roll", 45000, "Makanan"));
        menu.add(new MenuItem("Ramen", 40000, "Makanan"));
        menu.add(new MenuItem("Tempura", 35000, "Makanan"));
        menu.add(new MenuItem("Miso Soup", 20000, "Makanan"));
        menu.add(new MenuItem("Ocha Dingin/Panas", 10000, "Minuman"));
        menu.add(new MenuItem("Es Teh Manis", 12000, "Minuman"));
        menu.add(new MenuItem("Iced Coffee", 15000, "Minuman"));
        menu.add(new MenuItem("Cappuccino", 20000, "Minuman"));

        System.out.println("---Selamat Datang di Restoran Kiri---");
        System.out.println("Berikut adalah daftar menu kami:");

        // Buat daftar tampilan yang memetakan nomor ke item (setelah pemisahan kategori)
        ArrayList<MenuItem> displayList = new ArrayList<>();
        int nomorMenu = 1;
        System.out.println("\n== Makanan ==");
        for (MenuItem item : menu) {
            if ("Makanan".equals(item.getCategory())) {
                System.out.println(nomorMenu + ". " + item.getName() + " - Rp " + item.getPrice());
                displayList.add(item);
                nomorMenu++;
            }
        }
        System.out.println("\n== Minuman ==");
        for (MenuItem item : menu) {
            if ("Minuman".equals(item.getCategory())) {
                System.out.println(nomorMenu + ". " + item.getName() + " - Rp " + item.getPrice());
                displayList.add(item);
                nomorMenu++;
            }
        }

        ArrayList<OrderItem> orderItems = new ArrayList<>();
        int maksPesanan = 5;
        System.out.println("\nSilakan masukkan pesanan Anda (maksimal " + maksPesanan + " item):");
        System.out.println("Ketik '0' jika Anda sudah selesai memesan.");

        for (int i = 0; i < maksPesanan; i++) {
            System.out.print("---------------- ");
            System.out.print("Pesanan ke-" + (i + 1) + " (Masukkan nomor menu): ");
            if (!input.hasNextInt()) {
                System.out.println("Input harus berupa angka. Coba lagi.");
                input.next(); // buang token
                i--;
                continue;
            }
            int nomorPilihan = input.nextInt();

            if (nomorPilihan == 0) {
                break;
            }

            if (nomorPilihan < 1 || nomorPilihan > displayList.size()) {
                System.out.println("Nomor menu tidak valid. Silakan coba lagi.");
                i--;
                continue;
            }

            MenuItem selectedItem = displayList.get(nomorPilihan - 1);
            System.out.print("Jumlah " + selectedItem.getName() + " yang dipesan: ");
            if (!input.hasNextInt()) {
                System.out.println("Input harus berupa angka. Coba lagi.");
                input.next();
                i--;
                continue;
            }
            int jumlahPesan = input.nextInt();

            if (jumlahPesan <= 0) {
                System.out.println("Jumlah pesanan harus lebih dari 0. Silakan coba lagi.");
                i--;
                continue;
            }

            orderItems.add(new OrderItem(selectedItem, jumlahPesan));
            System.out.println(selectedItem.getName() + " sebanyak " + jumlahPesan + " telah ditambahkan ke pesanan Anda.");
        }

        System.out.println("\n--- Ringkasan Pesanan Anda ---");
        int totalBayar = 0;
        if (orderItems.isEmpty()) {
            System.out.println("Anda tidak memesan apa-apa.");
        } else {
            for (OrderItem orderItem : orderItems) {
                MenuItem item = orderItem.getMenuItem();
                int quantity = orderItem.getQuantity();
                int itemTotal = orderItem.getTotalPrice();
                totalBayar += itemTotal;
                System.out.println(item.getName() + " x " + quantity + " = Rp " + itemTotal);
            }
            System.out.println("Total yang harus dibayar: Rp " + totalBayar);
        }

        System.out.println("\n--- Detail Pembayaran ---");
        double pajakRate = 0.10;
        double serviceRate = 0.05;
        double discount = 0.0;
        double bogoDiscount = 0.0;

        double pajakAmount = totalBayar * pajakRate;
        double serviceAmount = totalBayar * serviceRate;

        if (totalBayar > 100000) {
            discount = 0.10 * totalBayar;
            System.out.println("Diskon 10% telah diterapkan.");
        }

        if (totalBayar > 50000) {
            int cheapestDrinkPrice = Integer.MAX_VALUE;
            boolean hasBeverage = false;
            // Cari minuman termurah yang dipesan
            for (OrderItem orderItem : orderItems) {
                MenuItem item = orderItem.getMenuItem();
                if ("Minuman".equals(item.getCategory()) && orderItem.getQuantity() > 0) {
                    hasBeverage = true;
                    if (item.getPrice() < cheapestDrinkPrice) {
                        cheapestDrinkPrice = item.getPrice();
                    }
                }
            }
            if (hasBeverage && cheapestDrinkPrice != Integer.MAX_VALUE) {
                // BOGO: gratis 1 unit minuman termurah
                bogoDiscount = cheapestDrinkPrice;
                System.out.println("BOGO Discount telah diterapkan pada minuman termurah seharga Rp " + cheapestDrinkPrice);
            }
        }

        double grandTotal = totalBayar + pajakAmount + serviceAmount - discount - bogoDiscount;

        System.out.println("Subtotal: Rp " + totalBayar);
        System.out.println("Pajak (10%): Rp " + pajakAmount);
        System.out.println("Service Charge (5%): Rp " + serviceAmount);
        System.out.println("Diskon: Rp " + discount);
        System.out.println("BOGO Diskon: Rp " + bogoDiscount);
        System.out.println("---------------------------");
        System.out.println("Total Pembayaran: Rp " + grandTotal);

        input.close();
    }
}
