package ua.meta.atipikin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
	static EntityManagerFactory emf;
	static EntityManager em;
	public static void main(String[] args) {
		List<Peach> list3 = createList(); // створюємо список об'єктів для запису в базу даних
		try {
			emf = Persistence.createEntityManagerFactory("Homework9download");
			em = emf.createEntityManager();
			// заповнюємо таблицю через транзакцію
			em.getTransaction().begin();
			try {
				for(Peach p: list3) em.merge(p); // записуємо об'єкти в базу даних поодинці
				em.getTransaction().commit();
			} catch (Exception ex) {
				em.getTransaction().rollback();
			}
		} finally {
			em.close();
			emf.close();
		}
	}
	
	public static List<Peach> createList() { // список об'єктів для запису в базу даних
		List<String> list1 = new ArrayList<>(List.of("Ред Робін", "Редхейвен", "Світ рінг", "Фіделія", "Харнас", "Шугар тайм"));
		List<String> list2 = new ArrayList<>(List.of("https://dobrodar.ua/uploads/cache/Products/Product_images_40640/cfa985_w508.jpeg",
				"https://dobrodar.ua/uploads/cache/Products/Product_images_38407/8c1042_w508.jpg",
				"https://litynsad.com.ua/sites/default/files/styles/570x415/public/product_images/persik-sweet-ring_0.jpg?itok=Wa7FUXee",
				"https://dobrodar.ua/uploads/cache/Products/Product_images_43476/6d91c0_w508.jpg",
				"https://flora-ua.com/image/cache/catalog/GOODS/PLODOVI/persyk/Harnas/harnas1-600x600.jpg",
				"https://litynsad.com.ua/sites/default/files/styles/570x415/public/product_images/persik-shugar-taym.jpg?itok=qYIchmPz"));
		List<Peach> list3 = new ArrayList<>();
		for(int i = 0; i < list1.size(); i++) {
			String name = list1.get(i); // назва фотки
			byte[] byteArray = null;
			String image = list2.get(i); // адреса фотки в інтернеті
			try {
				byteArray = uploadPicture(image); // перетворення фотки на байтовий масив
				String imageFile = name + ".jpg";
				createFileFromArray(byteArray, imageFile); // збережемо картинку у вигляді файлу (необов'язково)
			} catch (IOException e) {
				e.printStackTrace();
			}
			Peach peach = new Peach(name, byteArray); // створюємо новий об'єкт класу "Персик"
			list3.add(peach); // додаємо останній в список об'єктів Peach
		}
		return list3;
	}
	
	public static byte[] uploadPicture(String image) throws IOException { // перетворення фотки на байтовий масив
		URL url = new URL(image);
		InputStream input = url.openStream();
		byte[] b1 = input.readAllBytes();
		return b1;
	}
	
	public static void createFileFromArray(byte[] b1, String fileName) throws IOException { // збережемо картинку у вигляді файлу
		FileOutputStream fos = new FileOutputStream(fileName);
    	fos.write(b1);
    	fos.close();
	}
}