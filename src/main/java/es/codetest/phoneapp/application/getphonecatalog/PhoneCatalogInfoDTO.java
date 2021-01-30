package es.codetest.phoneapp.application.getphonecatalog;

public class PhoneCatalogInfoDTO {

  private final String name;
  private final String description;
  private final String image;
  private final Integer price;

  public PhoneCatalogInfoDTO(String name, String description, String image, Integer price) {
    this.name = name;
    this.description = description;
    this.image = image;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getImage() {
    return image;
  }

  public Integer getPrice() {
    return price;
  }
}
