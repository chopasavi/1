package library;

import java.util.List;

public class PetPojo {
    public PetPojo(long id, String name, List<String> photoUrls) {
        this.id = id;
        this.name = name;
        this.photoUrls = photoUrls;
    }
    
    public PetPojo(String name, List<String> photoUrls) {
        this.name = name;
        this.photoUrls = photoUrls;
    }

    public PetPojo(){
    }

    public long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;

    public static class Category {
        public int id;
        public String name;
    }

    public static class Tag {
        public int id;
        public String name;
    }
}
