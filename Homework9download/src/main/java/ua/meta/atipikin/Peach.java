package ua.meta.atipikin;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="peach")
public class Peach {
	@Id
	private String name;
	@Lob
	private byte[] image; // в базі MySQL тип LONGBLOB
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
}