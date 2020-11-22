package store;

public class PhotoBean
{
  String stname;

  public PhotoBean()
  {
  }
  
  public PhotoBean(String pstname)
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
