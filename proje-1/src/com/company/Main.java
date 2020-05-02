package com.company;

import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner dosya = null;
        try {
            dosya = new Scanner(new FileInputStream("Firma.txt"));
            System.out.println("Dosyadan alınan müşteriler ile ilgili bilgiler yazdırılıyor...");
        }catch(FileNotFoundException e) {
            System.out.println("Dosya bulunamadı.");
            System.exit(0);
        }
        // tanımlar bloğu
        int ürünSayısı = 0;
        int sayaç = -1; // ürün sayısını buldurmak için -1
        int nationalSay = 0;
        int internationalSay = 0;
        int doktorSay = 0;
        Customer[] müşteriler = new Customer[200]; // müşteri sayısında üst sınır 200
        int[][] derecelendirmeler = new int[200][]; // ikinci [] ürün sayısına göre belirlenecek

        while (dosya.hasNextLine()){
            StringTokenizer satır = new StringTokenizer(dosya.nextLine(),(", "));
            String şimdikiKelime = satır.nextToken();
            if (sayaç == -1 & (!şimdikiKelime.equals("n") || !şimdikiKelime.equals("i"))){
                ürünSayısı = satır.countTokens(); // ilk satır ürün sayısını verir
                for(int i = 0; i < 200; i++){
                    derecelendirmeler[i] = new int[ürünSayısı]; //ürün sayısı her müşteri için aynı
                }
                sayaç++;
                continue;
            }
            if (şimdikiKelime.equals("n") || şimdikiKelime.equals("i")){
                int customerID = Integer.parseInt(satır.nextToken(",")); //ortak parametreler
                String name = satır.nextToken(",");
                String surname = satır.nextToken(",");
                if (şimdikiKelime.equals("n")){
                    int licencePlateNum = Integer.parseInt(satır.nextToken(","));
                    String occupation = satır.nextToken(",");
                    if (occupation.equals("Doktor")){
                        doktorSay++;
                    }
                    NationalCustomer müşteri = new NationalCustomer(customerID, name, surname, licencePlateNum, occupation);
                    müşteriler[sayaç] = müşteri;
                    nationalSay++;
                }else { //ilk if ile kontrol edildiği için else if yazmaya gerek yok
                    String country = satır.nextToken(",");
                    String city = satır.nextToken(",");
                    InternationalCustomer müşteri = new InternationalCustomer(customerID, name, surname, country, city);
                    müşteriler[sayaç] = müşteri;
                    internationalSay++;
                }
                //derecelendirmelerin listeye alınmasıyla ilgili işlemler
                StringTokenizer ürünSatırı = new StringTokenizer(dosya.nextLine(),(", ")); //müşteriyi aldıktan sonra yeni satıra geçmesi gerek
                for(int i = 0; i < ürünSayısı; i++){
                    derecelendirmeler[sayaç][i] = Integer.parseInt(ürünSatırı.nextToken(","));
                }
            }sayaç++;
        }
        for (int i = 0; i < ürünSayısı; i++) { //dosyadan alınan ürünlerin ortalamasını hesaplayan döngü
            float toplam = 0; // her ürün için sıfırlanması gerektiği için for içinde tanımlandı
            for (int j = 0; j < sayaç; j++) {
                toplam += derecelendirmeler[j][i];
            }
            float ortalama = toplam / sayaç;
            System.out.print(i + 1 + ". ürünün ortalama puanı: ");
            System.out.printf("%.2f%n", ortalama);
        }
        System.out.println();

        float[] ortalamaPuanN = new float[ürünSayısı];
        float[] ortalamaPuanI = new float[ürünSayısı];
        for (int i = 0; i < ürünSayısı; i++) { // müşteri türlerine göre ortalama puanlarını hesaplayan döngü
            float toplamN = 0, toplamI = 0, toplamDoktor = 0;
            float ortalamaN, ortalamaI, ortalamaDok;
            for (int j = 0; j < nationalSay + internationalSay; j++) {
                Customer müşteri = müşteriler[j];
                if (müşteri instanceof NationalCustomer){
                    toplamN += derecelendirmeler[j][i];
                    if (((NationalCustomer) müşteri).getOccupation().equals("Doktor")){
                        toplamDoktor += derecelendirmeler[j][i];
                    }
                }else if (müşteriler[j] instanceof InternationalCustomer){
                    toplamI += derecelendirmeler[j][i];
                }
            }
            ortalamaI = toplamI / internationalSay;
            ortalamaN = toplamN / nationalSay;
            ortalamaDok = toplamDoktor / doktorSay;
            ortalamaPuanI[i] = ortalamaI;
            ortalamaPuanN[i] = ortalamaN;
            System.out.print(i + 1 + ". ürünün ulusal ortalama puanı: ");
            System.out.printf("%.2f%n", ortalamaN);
            System.out.print(i + 1 + ". ürünün uluslararası ortalama puanı: ");
            System.out.printf("%.2f%n", ortalamaI);
            System.out.print(i + 1 + ". ürünün doktorlar arası ortalama puanı: ");
            System.out.printf("%.2f%n", ortalamaDok);
            System.out.println();
        }

        for (int i = 0; i < ürünSayısı; i++) { // ortalama altında puan veren müşterileri yazdıran döngü
            System.out.println(i + 1 + ". ürün için ortalama altı puan veren kullanıcılar:");
            for (int j = 0; j < sayaç; j++) {
                if (müşteriler[j] instanceof NationalCustomer && derecelendirmeler[j][i] < ortalamaPuanN[i]){
                    System.out.println(müşteriler[j]);
                }else if (müşteriler[j] instanceof InternationalCustomer && derecelendirmeler[j][i] < ortalamaPuanI[i]){
                    System.out.println(müşteriler[j]);
                }
            }
            System.out.println();
        }
        //-1 girilirse klavyeden girişler sonlanır
        Scanner input = new Scanner(System.in);
        int[] benzerlik = new int[199]; // benzer müşteriler için liste
        int klavyeSayacı = 0;
        String müşTip, ad, meslek, soyad, ülke, şehir;
        int müşNo, plaka;

        for (int i = 0; i < 200 - sayaç; i++) { // klavyeden müşteri bilgilerini alan döngü
            do {
                System.out.println("Müşteri tipi (n/i) [çıkış için -1]: ");
                müşTip = input.nextLine();
            }while (!(müşTip.equals("n") || müşTip.equals("i") || müşTip.equals("-1")));
            if (müşTip.equals("-1")) break;
            input.nextLine(); // Buffer temizlemek amacıyla yapıldı

            System.out.println("Müşteri numarası [çıkış için -1]: ");
            müşNo = input.nextInt();
            if (müşNo == -1) break;
            input.nextLine();

            System.out.println("Müşterinin adı [çıkış için -1]: ");
            ad = input.nextLine();
            if (ad.equals("-1")) break;

            System.out.println("Müşterinin soyadı [çıkış için -1]: ");
            soyad = input.nextLine();
            if (soyad.equals("-1")) break;

            if (müşTip.equals("-1")){
                do {
                    System.out.println("Plaka [çıkış için -1]: ");
                    plaka = input.nextInt();
                }while (plaka > 81 && plaka < 1);
                if (plaka == -1) break;
                input.nextLine();

                System.out.println("Meslek [çıkış için -1]: ");
                meslek = input.nextLine();
                if (meslek.equals("-1")) break;

                NationalCustomer müşteri = new NationalCustomer(müşNo, ad, soyad, plaka, meslek);
                müşteriler[sayaç + i] = müşteri; // sayaç sabit bırakılıp indis i kadar arttırıldı
            }
            if (müşTip.equals("i")){
                System.out.println("Ülke [çıkış için -1]: ");
                ülke = input.nextLine();
                if (ülke.equals("-1")) break;

                System.out.println("Şehir [çıkış için -1]: ");
                şehir = input.nextLine();
                if (şehir.equals("-1")) break;

                InternationalCustomer müşteri = new InternationalCustomer(müşNo, ad, soyad, ülke, şehir);
                müşteriler[sayaç + i] = müşteri; // sayaç sabit bırakılıp indis i kadar arttırıldı
            }
            for (int j = 0; j < ürünSayısı - 1; j++) { // klavyeden müşteri derecelendirmelerini alan döngü
                System.out.println(j + 1 + ". ürünün puanı [çıkış için 1'den küçük ya da 5'ten büyük bir değer]: ");
                int puan = input.nextInt();
                if (puan < 0 || puan > 5) break;
                derecelendirmeler[sayaç + i][j] = puan;
            }
            for (int j = 0; j < nationalSay + internationalSay; j++) { // benzerlik oranlarını bulan döngü
                int benzerlikOranı = 0;
                for (int k = 0; k < ürünSayısı - 1; k++) {
                    benzerlikOranı += Math.abs(derecelendirmeler[sayaç + i][k] - derecelendirmeler[j][k]);
                }
                benzerlik[j] = benzerlikOranı;
            }
            // klavyeden alınacak son ürünün puanının hesaplanması
            float minToplam = 0, minSay = 0;
            int benzeyenMüşIndex = minimumIndis(benzerlik);
            for(int j = 0; j < benzerlik.length; j++){
                if (benzerlik[j] == 0) break;
                if (benzerlik[benzeyenMüşIndex] == benzerlik[j]){
                    minToplam += derecelendirmeler[j][ürünSayısı - 1];
                    minSay++;
                }
            }
            float tahmin = minToplam / minSay;
            System.out.print(ürünSayısı + ". ürünün puanı ");
            System.out.printf("%.2f%s%n", tahmin, " mi?");
            System.out.println(ürünSayısı + ". ürünün puanı: ");
            int puan = input.nextInt();
            derecelendirmeler[sayaç + i][ürünSayısı - 1] = puan; //son ürün puanının listeye eklenmesi
            input.nextLine();
            klavyeSayacı++;
        }
        System.out.println();

        System.out.println("Klavyeden alınan müşteriler ile ilgili bilgiler yazdırılıyor...\n");
        for (int i = sayaç; i < 200 - sayaç; i++){ // klavyeden alınabilecek müşteri sayısı kadar döner
            if (müşteriler[i] == null) { // alınan müşteri sayısı 200'den küçükse listede null olacağı için yapıldı
                System.out.println();
                break;
            }
            System.out.println(müşteriler[i]);
            for (int j = 0; j < ürünSayısı; j++) {
                System.out.print(derecelendirmeler[i][j] + " ");
            }
            System.out.println();
        }

        for (int i = 0; i < ürünSayısı; i++) { // klavyeden alınan ürünlerin ortalama puanlarını hesaplayan döngü
            float toplam = 0;
            for (int j = sayaç; j < sayaç + klavyeSayacı; j++) {
                toplam += derecelendirmeler[j][i];
            }
            float ortalama = toplam / klavyeSayacı;
            System.out.print(i + 1 + ". ürünün ortalama puanı: ");
            System.out.printf("%.2f%n", ortalama);
        }
        dosya.close(); // açılan dosyanın kapatılması
    }

    public static int minimumIndis(int[] dizi){ // min benzerlik oranının indisini bulmak için metod
        int indis = 0;
        int min = dizi[indis];
        for (int i=1; i<dizi.length; i++){
            if (dizi[i] == 0){
                continue;
            }
            if (dizi[i] < min){
                min = dizi[i];
                indis = i;
            }
        }
        return indis;
    }
}
