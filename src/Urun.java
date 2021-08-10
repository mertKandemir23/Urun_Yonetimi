import java.io.*;
import java.math.BigDecimal;;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;
/*Ürün Yönetimi Ödevi
        -id(Long),title(String),desc(String),amount(BigDecimal), createdDate(Date) şekilnde ürün attribute leri olacak
        -Yazılacak bir java uygulamasında console dan yeni kayıt ekle, sil, listese şeklinde 3 menü ile seçim yapılacak
        -Kayırlar bir txt dosyasında satır bazlı tutulacak
        -Attribute ler her satırda tab separeted olarak tutulacak.*/

public class Urun {

    private long id; //Ürün id
    private String title; //Ürün başlığı
    private String desc; //ürün açıklaması
    private BigDecimal amount; //Ürün değeri

    private Date createdDate; //Ürün oluşturulma tarhi
    private static SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy");

    private static Map<Long, Urun> tumUrunler = new HashMap<>();

    public Urun(long id, String title, String desc, BigDecimal amount, Date createdDate) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.amount = amount;
        this.createdDate = createdDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void urunEkle(File productsFile) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(productsFile, true);
        OutputStreamWriter writer =
                new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        writer.write(this.urunYazilmaTipiniDuzenle() + "\n");
        writer.close();
        Urun.tumUrunler.put(this.id, this);
        System.out.println("Ürün başarıyla eklendi.");
    }

    public String urunYazilmaTipiniDuzenle() {
        return this.id + "\t" + this.title + "\t" + this.desc + "\t" + this.amount + "\t" + dt.format(this.createdDate);
    }

    @Override
    public String toString() {
        return "Ürün ID : " +id+"\n"+"Ürün Başlığı :  "+title+"\n"+"Ürün açıklama : "+desc+"\n"+
                "Ürün oluşturulma tarihi : "+createdDate;
    }

    public static void urunleriDosyadanOku(File dosya) throws IOException {
        Stream<String> satirlar = Files.lines(dosya.toPath());
        satirlar.forEach(satir -> {
            try {
                satirdanUrunEkle(satir);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        satirlar.close();
    }

    private static void satirdanUrunEkle(String satir) throws ParseException {
        String urunOzellikleri[] = satir.split("\t"); //Burada tab seperate için ayarlama yaptım
        long id = Long.parseLong(urunOzellikleri[0]);
        String title = urunOzellikleri[1];
        String desc = urunOzellikleri[2];
        BigDecimal amount = new BigDecimal(urunOzellikleri[3]);
        Date createdDate = dt.parse(urunOzellikleri[4]);

        tumUrunler.put(id, new Urun(id, title, desc, amount, createdDate));
    }

    public static void urunSil(long id, File urunDosyasi) throws IOException {
        if (!Urun.tumUrunler.containsKey(id)) { //Eğer ürün id'yi içermiyorsa
            System.out.println("Urun bulunamadi");
            return;
        }
        FileOutputStream fileOutputStream = new FileOutputStream(urunDosyasi);
        OutputStreamWriter writer =
                new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
        tumUrunler.remove(id);

        tumUrunler.values().forEach(product -> {
            try {
                writer.write(product.urunYazilmaTipiniDuzenle() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        writer.close();
    }

    public static Collection<Urun> getTumUrunler() {
        return Urun.tumUrunler.values();
    }


}


