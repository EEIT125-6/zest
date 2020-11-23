package Store;

public class photoBean
{
  String stname;

  public photoBean()
  {
  }
  
  public photoBean(String pstname)
  {
    this.stname = pstname;
  }
  

  public void setStname(String pStname)
  {
    this.stname=pStname;
  }

  public String getStname()
  {
    return stname;
  }


}