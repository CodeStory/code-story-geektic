package geeks;

import lombok.Data;
import lombok.NonNull;

@Data
public class Geek {
	@NonNull
	String nom;
	String like1;
	String like2;
	String like3;
}
