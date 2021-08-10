import java.io.*;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Scanner;

public class Main {


        String dosyaYolu = new File("").getAbsolutePath();
        File urunListesi = new File(dosyaYolu.concat("/src/urunler.txt"));

        Urun.urunleriDosyadanOku(urunListesi);
        Scanner input = new Scanner(System.in);System.out.println("Lütfen yapmak istediğiniz işlemi giriniz :");
        System.out.println("[1]YENİ KAYIT EKLE \n[2]KAYIT SİL\n[3]LİSTELE\n[4]ÜRÜN MİKTARINI GÖSTER ");
        int secim = input.nextInt();

        long id;

        while (secim != -1) {
            switch (secim)
            {
                case 1:
                    System.out.print("ID: ");
                    id = input.nextLong();

                    System.out.print("Başlık: ");
                    String title = input.next();

                    input.nextLine();
                    System.out.print("\nAçıklama: ");

                    String desc = input.nextLine();
                    System.out.print("\nMiktar: ");

                    BigDecimal amount = input.nextBigDecimal();
                    Date createdDate = new Date();

                    Urun product = new Urun(id, title, desc, amount, createdDate);
                    product.urunEkle(urunListesi);

                    break;

                case 2 :
                    Urun.getTumUrunler().forEach(p -> {
                        System.out.println(p.getId()+"\t"+p.getTitle());
                    });

                    System.out.print("Silmek istediginiz urununun ID'sini giriniz: ");

                    id = input.nextLong();

                    Urun.urunSil(id, urunListesi);

                    System.out.println("Ürün sistemden başarıyla silinmiştir.");
                    break;




                case 3 :
                    Urun.getTumUrunler().forEach(p -> {
                        System.out.println(p.toString());
                    });
                    break;

                case 4:
                    System.out.println("Sistemdeki kayıtlı olan ürün miktarı : " + Urun.getTumUrunler().size());




            }
            secim = input.nextInt();
        }
    }
}
