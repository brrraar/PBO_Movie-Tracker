public class Film {
    public int id;
    public int userId;
    public String judul;
    public String tanggalRilis;
    public String genre;
    public String karakter;
    public String komentar;
    public Double rating;
    public String posterPath;

    // Constructor kosong agar bisa dipakai Film()
    public Film() {
    }

    @Override
    public String toString() {
        return judul;
    }
}
