package com.ahmettekin.calculateaverage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.shashank.sony.fancytoastlib.FancyToast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dynamic_layout.*
import kotlinx.android.synthetic.main.dynamic_layout.view.*
import kotlinx.android.synthetic.main.toast_tasarim.view.*

class MainActivity : AppCompatActivity() {

    private val DERSLER = arrayOf("Matematik", "Türkçe", "Fizik", "Edebiyat", "Algoritma", "Tarih")
    private val tumDerslerinBilgileri = ArrayList<Dersler>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, DERSLER)

        etDersAd.setAdapter(adapter)

        if (rootLayout.childCount == 0) btnHesapla.visibility = View.INVISIBLE
        else btnHesapla.visibility = View.VISIBLE


        btnDersEkle.setOnClickListener {

            if (!etDersAd.text.isNullOrEmpty()) {
                val yeniDersView = layoutInflater.inflate(R.layout.dynamic_layout, null)
                //statik alandan kullanıcının girdiği değerleri alalım

                yeniDersView.etYeniDersAd.setAdapter(adapter)
                yeniDersView.etYeniDersAd.setText(etDersAd.text.toString())
                yeniDersView.spnYeniDersKredi.setSelection(spnDersKredi.selectedItemPosition)
                yeniDersView.spnYeniDersNot.setSelection(spnDersNot.selectedItemPosition)

                /**aşağıdaki fonksiyonu btnDersEkle listener'ın
                içine yazıyoruz, btnDersSil butonuna tıklandığında
                remove yap tanımlaması yapıyoruz.
                Sonra, "rootLayout.addView(yeniDersView)" satırı ile, btnDersSil
                butonuna tıklandığında yapılacak olan silme işleminin tanımını,
                rootlayout'a eklemiş oluyoruz
                Yani bu silme tanımı, xml'de bulunan tıpkı bir textbox gibi,
                ya da başka bir buton tanımı gibi, artık rootLayout'a ekli,
                ama bir fonksiyon(listener) olarak.
                 */

                yeniDersView.btnDersSil.setOnClickListener {
                    rootLayout.removeView(yeniDersView)
                    if (rootLayout.childCount == 0) btnHesapla.visibility = View.INVISIBLE
                    else btnHesapla.visibility = View.VISIBLE
                }
                //dinamik olarak linearlayouta ekledik.
                rootLayout.addView(yeniDersView)
                btnHesapla.visibility = View.VISIBLE
                sifirla()

            } else FancyToast.makeText(
                this,
                "Lütfen Bir Ders Giriniz",
                FancyToast.LENGTH_LONG,
                FancyToast.ERROR,
                false
            ).show()
        }
    }


   /*  fun ortalamaHesapla(view: View) {


         var toplamKredi = 0
         var toplamNot=0.0
         for (tempView in rootLayout.children) {
             var katsayi = 4 - 0.5 * (tempView.spnYeniDersNot.selectedItemPosition)
             val kredi: Int = tempView.spnYeniDersKredi.selectedItem.toString().split(" ")[0].toInt()
             toplamKredi += kredi
             toplamNot += kredi*katsayi
         }
         Toast.makeText(this, "ortalama: ${toplamNot/toplamKredi}", Toast.LENGTH_SHORT).show()
     }
*/
    fun sifirla() {
        etDersAd.setText("")
        spnDersKredi.setSelection(0)
        spnDersNot.setSelection(0)
    }

    fun ortalamaHesapla(view: View) {

        var toplamNot = 0.0
        var toplamKredi = 0.0
        for (i in 0 until rootLayout.childCount) {
            var tekSatir = rootLayout.getChildAt(i)

            val dersAdi = tekSatir.etYeniDersAd.text.toString()
            val dersKredi = (tekSatir.spnYeniDersKredi.selectedItemPosition + 1).toString()
            val dersNot = tekSatir.spnYeniDersNot.selectedItem.toString()
            var geciciDers = Dersler(dersAdi, dersKredi, dersNot)

            tumDerslerinBilgileri.add(geciciDers)
        }

        for (oankiDers in tumDerslerinBilgileri) {

            toplamNot += harfiNotaCevir(oankiDers.dersHarfNot) * (oankiDers.dersKredi.toDouble())
            toplamKredi += oankiDers.dersKredi.toDouble()
        }

        val toastView = layoutInflater.inflate(R.layout.toast_tasarim, null)
        toastView.textView.text = "ORTALAMA : ${toplamNot / toplamKredi}"


        FancyToast.makeText(
            this,
            "ORTALAMA : ${toplamNot / toplamKredi}",
            FancyToast.LENGTH_LONG,
            FancyToast.WARNING,
            R.drawable.ic_baseline_pedal_bike_24,
            false
        ).show()

        tumDerslerinBilgileri.clear()
    }

    fun harfiNotaCevir(str: String): Double {

        val deger = when (str) {
            "AA" -> 4.0
            "BA" -> 3.5
            "BB" -> 3.0
            "CB" -> 2.5
            "CC" -> 2.0
            "DC" -> 1.5
            "DD" -> 1.0
            "FD" -> 0.5
            "FF" -> 0.0
            else -> 0.0
        }

        return deger
    }
}



