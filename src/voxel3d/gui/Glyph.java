package voxel3d.gui;

import java.util.TreeMap;

import voxel3d.utility.Vector2f;

public class Glyph {
	
	
	private static final TreeMap<Character, Glyph> glyphs = new TreeMap<Character, Glyph>();
	
	static {
		new Glyph(new Vector2f(0, 6), '0');
		new Glyph(new Vector2f(1, 6), '1');
		new Glyph(new Vector2f(2, 6), '2');
		new Glyph(new Vector2f(3, 6), '3');
		new Glyph(new Vector2f(4, 6), '4');
		new Glyph(new Vector2f(5, 6), '5');
		new Glyph(new Vector2f(6, 6), '6');
		new Glyph(new Vector2f(7, 6), '7');
		new Glyph(new Vector2f(8, 6), '8');
		new Glyph(new Vector2f(9, 6), '9');
		
		new Glyph(new Vector2f(6, 2), ' ');
		
		new Glyph(new Vector2f(0, 3), 'a');
		new Glyph(new Vector2f(1, 3), 'b');
		new Glyph(new Vector2f(2, 3), 'c');
		new Glyph(new Vector2f(3, 3), 'd');
		new Glyph(new Vector2f(4, 3), 'e');
		new Glyph(new Vector2f(5, 3), 'f');
		new Glyph(new Vector2f(6, 3), 'g');
		new Glyph(new Vector2f(7, 3), 'h');
		new Glyph(new Vector2f(8, 3), 'i');
		new Glyph(new Vector2f(9, 3), 'j');
		new Glyph(new Vector2f(0, 4), 'k');
		new Glyph(new Vector2f(1, 4), 'l');
		new Glyph(new Vector2f(2, 4), 'm');
		new Glyph(new Vector2f(3, 4), 'n');
		new Glyph(new Vector2f(4, 4), 'o');
		new Glyph(new Vector2f(5, 4), 'p');
		new Glyph(new Vector2f(6, 4), 'q');
		new Glyph(new Vector2f(7, 4), 'r');
		new Glyph(new Vector2f(8, 4), 's');
		new Glyph(new Vector2f(9, 4), 't');
		new Glyph(new Vector2f(0, 5), 'u');
		new Glyph(new Vector2f(1, 5), 'v');
		new Glyph(new Vector2f(2, 5), 'w');
		new Glyph(new Vector2f(3, 5), 'x');
		new Glyph(new Vector2f(4, 5), 'y');
		new Glyph(new Vector2f(5, 5), 'z');
		
		new Glyph(new Vector2f(0, 0), 'A');
		new Glyph(new Vector2f(1, 0), 'B');
		new Glyph(new Vector2f(2, 0), 'C');
		new Glyph(new Vector2f(3, 0), 'D');
		new Glyph(new Vector2f(4, 0), 'E');
		new Glyph(new Vector2f(5, 0), 'F');
		new Glyph(new Vector2f(6, 0), 'G');
		new Glyph(new Vector2f(7, 0), 'H');
		new Glyph(new Vector2f(8, 0), 'I');
		new Glyph(new Vector2f(9, 0), 'J');
		new Glyph(new Vector2f(0, 1), 'K');
		new Glyph(new Vector2f(1, 1), 'L');
		new Glyph(new Vector2f(2, 1), 'M');
		new Glyph(new Vector2f(3, 1), 'N');
		new Glyph(new Vector2f(4, 1), 'O');
		new Glyph(new Vector2f(5, 1), 'P');
		new Glyph(new Vector2f(6, 1), 'Q');
		new Glyph(new Vector2f(7, 1), 'R');
		new Glyph(new Vector2f(8, 1), 'S');
		new Glyph(new Vector2f(9, 1), 'T');
		new Glyph(new Vector2f(0, 2), 'U');
		new Glyph(new Vector2f(1, 2), 'V');
		new Glyph(new Vector2f(2, 2), 'W');
		new Glyph(new Vector2f(3, 2), 'X');
		new Glyph(new Vector2f(4, 2), 'Y');
		new Glyph(new Vector2f(5, 2), 'Z');
		
		new Glyph(new Vector2f(0, 7), '.');
		new Glyph(new Vector2f(1, 7), ',');
		new Glyph(new Vector2f(2, 7), ':');
		new Glyph(new Vector2f(3, 7), '/');
		new Glyph(new Vector2f(4, 7), '-');
		new Glyph(new Vector2f(5, 7), '_');
	}
	
	public static final Glyph unknown = new Glyph(new Vector2f(7, 2), '\0');
	

	public static final float tileSize = 1f/10f;
	public static final float widthToHeight = 6f/8f;
	
	public final Vector2f uv;
	//private final char ch;
	
	public Glyph(Vector2f uv, char ch)
	{
		//this.ch = ch;
		this.uv = uv;
		glyphs.put(ch, this);
	}

	public static Glyph getGlyph(char ch)
	{
		Glyph glyph = glyphs.get(ch);
		return glyph != null ? glyph : unknown;
	}
	
}
