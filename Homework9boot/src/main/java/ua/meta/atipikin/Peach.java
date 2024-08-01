package ua.meta.atipikin;

import java.util.Base64;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="peach")
public class Peach {
	@Id
	private String name;
	@Lob
	private byte[] image; // в базі MySQL тип LONGBLOB
	private boolean selected = false; // помічаємо обраний сорт
	public Peach() {
	}
	public Peach(String name, byte[] image) {
		this.name = name;
		this.image = image;
	}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public byte[] getImage() {return image;}
	public void setImage(byte[] image) {this.image = image;}
	public boolean getSelected() {return selected;}
	public void setSelected(boolean selected) {this.selected = selected;}
	
	public String convertBase64() {
		return Base64.getEncoder().encodeToString(this.image);
	}
}