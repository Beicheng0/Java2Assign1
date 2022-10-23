import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MovieAnalyzer {
  static List<String> name = new ArrayList<>();
  static List<Integer> year = new ArrayList<>();
  List<String> Certificate = new ArrayList<>();
  static List<String> Runtime = new ArrayList<>();
  static List<String> Genre = new ArrayList<>();
  static List<String> IMDB_Rating = new ArrayList<>();
  static List<String> Overview = new ArrayList<>();
  List<String> Meta_score = new ArrayList<>();
  List<String> Director = new ArrayList<>();
  static List<String> Star1 = new ArrayList<>();
  static List<String> Star2 = new ArrayList<>();
  static List<String> Star3 = new ArrayList<>();
  static List<String> Star4 = new ArrayList<>();
  List<String> Noofvotes = new ArrayList<>();
  static List<String> Gross = new ArrayList<>();

  public MovieAnalyzer(String dataset_path) throws IOException {
    try {
        BufferedReader reader=new BufferedReader(new FileReader(dataset_path,StandardCharsets.UTF_8));
      String a;
      reader.readLine();
      while ((a =reader.readLine())!= null){
        String[]arr = a.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)", -1);
        name.add(arr[1]);
        year.add(Integer.parseInt(arr[2]));
        Certificate.add(arr[3]);
        Runtime.add(arr[4]);
        Genre.add(arr[5]);
        IMDB_Rating.add(arr[6]);
        Overview.add(arr[7]);
        Meta_score.add(arr[8]);
        Director.add(arr[9]);
        Star1.add(arr[10]);
        Star2.add(arr[11]);
        Star3.add(arr[12]);
        Star4.add(arr[13]);
        Noofvotes.add(arr[14]);
        Gross.add(arr[15]);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
//        for (int i = 0; i < Runtime.size(); i++) {
//            System.out.println(Runtime.get(i));
//        }
  }
  public  Map<Integer, Integer> getMovieCountByYear(){
        Map<Integer, Long> countYear = new TreeMap<>();
        countYear =year.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        Map<Integer,Integer> countYear1 = new TreeMap<>();
        Iterator it =countYear.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry entry = (Map.Entry) it.next();
            Integer key = (Integer) entry.getKey();
            countYear1.put(key,countYear.get(key).intValue());
        }
        countYear1 = ((TreeMap)countYear1).descendingMap();
        return countYear1;
    }
  public  Map<String, Integer> getMovieCountByGenre(){
        List<String>Genre1 =new ArrayList<>();
        String a;
        String [] b;
        for (int i = 0; i < Genre.size(); i++) {
            if (Genre.get(i).charAt(0)=='"'){
                a = Genre.get(i).substring(1,Genre.get(i).length()-1);
                b = a.split(", ");
                Genre1.addAll(Arrays.asList(b));
            }else Genre1.add(Genre.get(i));
        }

        Map<String,Integer> countGenre1 = new LinkedHashMap<>();
        Genre1.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream().sorted(Map.Entry.<String, Long>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey()))
                .forEachOrdered(e->{
                    countGenre1.put(e.getKey(), e.getValue().intValue());
                });


//        for (Map.Entry<String, Long> stringLongEntry : countGenre.entrySet()) {
//            String key = (String) ((Map.Entry<?, ?>) stringLongEntry).getKey();
//            countGenre1.put(key,countGenre.get(key).intValue());
//        }
//        List<Map.Entry<String, Integer>> entries = new ArrayList<>(countGenre1.entrySet());
//        Collections.sort(entries, (o1, o2) -> (o2.getValue() - o1.getValue()));

//        for (Map.Entry entry : entries) {
//            if (entry.getValue()==entry.getValue()){
//                String c = (String) entry.getKey();
//                char[] m = c.toCharArray();
//                Arrays.sort(m);
//            }
//            System.out.println(entry.getKey() + " == " + entry.getValue());
//        }
        System.out.println(countGenre1);
        return countGenre1;
    }
  public  Map<List<String>, Integer> getCoStarCount(){
    Map<List<String>, Integer> CoStarCount = new LinkedHashMap<>();
    List<String> CoStar = new ArrayList<>();
    List<List<String>> sumStar = new ArrayList<>();

    for (int i = 0; i < name.size(); i++) {
      CoStar.add(Star1.get(i));CoStar.add(Star2.get(i));if (!Objects.equals(CoStar.get(0), "") && !Objects.equals(CoStar.get(1), "")){CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);}CoStar = new ArrayList<>();
      CoStar.add(Star1.get(i));CoStar.add(Star3.get(i));if (!Objects.equals(CoStar.get(0), "") && !Objects.equals(CoStar.get(1), "")){CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);}CoStar = new ArrayList<>();
      CoStar.add(Star1.get(i));CoStar.add(Star4.get(i));if (!Objects.equals(CoStar.get(0), "") && !Objects.equals(CoStar.get(1), "")){CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);}CoStar = new ArrayList<>();
      CoStar.add(Star2.get(i));CoStar.add(Star3.get(i));if (!Objects.equals(CoStar.get(0), "") && !Objects.equals(CoStar.get(1), "")){CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);}CoStar = new ArrayList<>();
      CoStar.add(Star2.get(i));CoStar.add(Star4.get(i));if (!Objects.equals(CoStar.get(0), "") && !Objects.equals(CoStar.get(1), "")){CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);}CoStar = new ArrayList<>();
      CoStar.add(Star3.get(i));CoStar.add(Star4.get(i));if (!Objects.equals(CoStar.get(0), "") && !Objects.equals(CoStar.get(1), "")){CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);}CoStar = new ArrayList<>();
    }
    for (int i = 0; i < sumStar.size()-1; i++) {
      for (int j = i + 1; j <sumStar.size(); j++) {
        if (Objects.equals(sumStar.get(i).get(0), sumStar.get(j).get(0)) && Objects.equals(sumStar.get(i).get(1), sumStar.get(j).get(1))){
          sumStar.set(i,sumStar.get(j));
        }
      }
    }
    sumStar.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream().sorted(Map.Entry.<List<String>, Long>comparingByValue().reversed()
                ).forEachOrdered(e ->{
                    CoStarCount.put(e.getKey(),e.getValue().intValue());});
//    System.out.println(CoStarCount);
    return CoStarCount;
    }
  public  List<String> getTopMovies(int top_k, String by){
        List<String> TopMovies = new ArrayList<>();
        Map<List<String>,Integer> movies = new LinkedHashMap<>();
        List<List<String>> name1 = new ArrayList<>();
      for (int i = 0; i < name.size(); i++) {
          List<String> s = new ArrayList<>();
          if (name.get(i).charAt(0)=='"'){
              String a = name.get(i).substring(1,name.get(i).length()-1);
              s.add(String.valueOf(i));
              s.add(a);
          }else {
              s.add(String.valueOf(i));
              s.add(name.get(i));
          }
          name1.add(s);
      }
        if (Objects.equals(by, "runtime")){
            String [] c;
            List<Integer> runtime = new ArrayList<>();
            for (int i = 0; i < Runtime.size(); i++) {
                if (!Runtime.get(i).equals("")){
                    c = Runtime.get(i).split(" ");
                    runtime.add(Integer.valueOf(c[0]));
                }else runtime.add(0);
            }
            for (int i = 0; i < runtime.size(); i++) {
                movies.put(name1.get(i),runtime.get(i) );
            }
            movies.entrySet().stream().filter(m->m.getValue() != 0).sorted(Map.Entry.<List<String>,Integer>comparingByValue().reversed().thenComparing(p -> p.getKey().get(1))
                    ).forEachOrdered(e->{
                        TopMovies.add(e.getKey().get(1));
            });
        }
        if (Objects.equals(by, "overview")){
            List<Integer>overview = new ArrayList<>();
            for (int i = 0; i < Overview.size(); i++) {
                if (!Overview.get(i).equals("")){
                    int a;
                    if (Overview.get(i).charAt(0)=='"'){
                        a=Overview.get(i).length()-2;
                    }else a= Overview.get(i).length();
                    overview.add(a);
                }else overview.add(0);
            }
            for (int i = 0; i < overview.size(); i++) {
                movies.put(name1.get(i),overview.get(i) );
            }
            movies.entrySet().stream().filter(m->m.getValue() != 0).sorted(Map.Entry.<List<String>,Integer>comparingByValue().reversed().thenComparing(p->p.getKey().get(1))
            ).forEachOrdered(e->{
                TopMovies.add(e.getKey().get(1));
            });
        }
//        for (int i = 0; i < top_k; i++) {
//            System.out.println(TopMovies.get(i));
//        }
        return TopMovies.subList(0,top_k);
    }
  public  List<String> getTopStars(int top_k, String by) {
          List<String> TopStars = new ArrayList<>();
          if (Objects.equals(by, "rating")){
              Map<String, List<Double>> rating = new LinkedHashMap<>();
              Map<String, Double> rating1 = new LinkedHashMap<>();
              List<Double> Rating = new ArrayList<>();
              for (String s : IMDB_Rating) {
                  float a;
                  if (s.equals("")){
                      a = 0;
                  }
                  else {
                      a = Float.parseFloat(s);
                  }
                  Rating.add((double)a);
              }
              for (int i = 0; i < Star1.size(); i++) {
                  String key = Star1.get(i);
                  if (!key.equals("")){
                      if (rating.containsKey(Star1.get(i))) {
                          List<Double> Rating2 = rating.get(key);
                          Rating2.add(Rating.get(i));
                      } else{
                          List<Double> Rating2 = new ArrayList<>();
                          Rating2.add(Rating.get(i));
                          rating.put(key,Rating2);
                      }
                  }
              }
              for (int i = 0; i < Star2.size(); i++) {
                  String key = Star2.get(i);
                  if (!key.equals("")){
                      if (rating.containsKey(Star2.get(i))) {
                          List<Double> Rating2 = rating.get(key);
                          Rating2.add(Rating.get(i));
                      } else{
                          List<Double> Rating2 = new ArrayList<>();
                          Rating2.add(Rating.get(i));
                          rating.put(key,Rating2);
                      }
                  }

              }
              for (int i = 0; i < Star3.size(); i++) {
                  String key = Star3.get(i);
                  if (!key.equals("")){
                      if (rating.containsKey(Star3.get(i))) {
                          List<Double> Rating2 = rating.get(key);
                          Rating2.add(Rating.get(i));
                      } else{
                          List<Double> Rating2 = new ArrayList<>();
                          Rating2.add(Rating.get(i));
                          rating.put(key,Rating2);
                      }
                  }
              }
              for (int i = 0; i < Star4.size(); i++) {
                  String key = Star4.get(i);
                  if (!key.equals("")){
                      if (rating.containsKey(Star4.get(i))) {
                          List<Double> Rating2 = rating.get(key);
                          Rating2.add(Rating.get(i));
                      } else{
                          List<Double> Rating2 = new ArrayList<>();
                          Rating2.add(Rating.get(i));
                          rating.put(key,Rating2);
                      }
                  }
              }
              for (String key:rating.keySet()){
                  List<Double> a = rating.get(key);
                  double sum = 0;
                  int count = 0;
                  for (Double aDouble : a) {
                      if (aDouble==0){
                          count++;
                      }
                      sum += aDouble;
                  }
                  if (a.size() > count) {
                      double r = sum / (a.size() - count);
                      rating1.put(key,r);
                  }
              }
              rating1.entrySet().stream().sorted(Map.Entry.<String,Double>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
              ).forEachOrdered(e->{
//                  if (e.getKey().equals("Elijah Wood") || e.getKey().equals("Eli Wallach") || e.getKey().equals("Elliot Page"))
//                      System.out.println(e.getValue());
                  TopStars.add(e.getKey());
              });
          }
          if (Objects.equals(by, "gross")){
              Map<String, List<Double>> gross = new LinkedHashMap<>();
              Map<String, Double> gross1 = new LinkedHashMap<>();
              List<Double> Gross1 = new ArrayList<>();
              for (int i = 0; i < Gross.size(); i++) {
                  if (!Objects.equals(Gross.get(i), "")){
                      String[]a = Gross.get(i).split(",");
                      String b = "";
                      for (int j = 0; j < a.length; j++) {
                          b=b.concat(a[j]);
                      }
                      String c = b.substring(1,b.length()-1);
                      Gross1.add(Double.valueOf(c));
                  }else Gross1.add((double) 0);
              }
              for (int i = 0; i < Star1.size(); i++) {
                  String key = Star1.get(i);
                  if (!key.equals("")){
                      if (gross.containsKey(Star1.get(i))) {
                          List<Double> Rating2 = gross.get(key);
                          Rating2.add(Gross1.get(i));
                          gross.replace(key, Rating2);
                      } else{
                          List<Double> Rating2 = new ArrayList<>();
                          Rating2.add(Gross1.get(i));
                          gross.put(key,Rating2);
                      }
                  }

              }
              for (int i = 0; i < Star2.size(); i++) {
                      String key = Star2.get(i);
                      if (!key.equals("")){
                          if (gross.containsKey(Star2.get(i))) {
                              List<Double> Rating2 = gross.get(key);
                              Rating2.add(Gross1.get(i));
                              gross.replace(key, Rating2);
                          } else{
                              List<Double> Rating2 = new ArrayList<>();
                              Rating2.add(Gross1.get(i));
                              gross.put(key,Rating2);
                          }
                      }

              }
              for (int i = 0; i < Star3.size(); i++) {
                  String key = Star3.get(i);
                  if (!key.equals("")){
                      if (gross.containsKey(Star3.get(i))) {
                          List<Double> Rating2 = gross.get(key);
                          Rating2.add(Gross1.get(i));
                          gross.replace(key, Rating2);
                      } else{
                          List<Double> Rating2 = new ArrayList<>();
                          Rating2.add(Gross1.get(i));
                          gross.put(key,Rating2);
                      }
                  }
              }
              for (int i = 0; i < Star4.size(); i++) {
                  String key = Star4.get(i);
                  if (!key.equals("")){
                      if (gross.containsKey(Star4.get(i))) {
                          List<Double> Rating2 = gross.get(key);
                          Rating2.add(Gross1.get(i));
                          gross.replace(key, Rating2);
                      } else{
                          List<Double> Rating2 = new ArrayList<>();
                          Rating2.add(Gross1.get(i));
                          gross.put(key,Rating2);
                      }
                  }
              }
              for (String key:gross.keySet()){
                  List<Double> a = gross.get(key);
                  double sum = 0;
                  int count = 0;
                  for (Double aFloat : a) {
                      if (aFloat==0){
                          count++;
                      }
                      sum += aFloat;
                  }
                  if (a.size() > count) {
                      double r = sum / (a.size() - count);
                      gross1.put(key,r);
                  }
              }
              gross1.entrySet().stream().sorted(Map.Entry.<String,Double>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
              ).forEachOrdered(e->{
                  TopStars.add(e.getKey());
              });
          }
//          System.out.println(TopStars.subList(0,top_k));
          return TopStars.subList(0,top_k);
      }
  public  List<String> searchMovies (String genre,float min_rating, int max_runtime){
              List<String> movies = new ArrayList<>();
              String a;
              String[] b;
              String[] c;
              List<Integer> runtime = new ArrayList<>();
              List<Float> rating = new ArrayList<>();
              for (int i = 0; i < Runtime.size(); i++) {
                  if (!Runtime.get(i).equals("")){
                      c = Runtime.get(i).split(" ");
                      runtime.add(Integer.valueOf(c[0]));
                  }else runtime.add(100000);
              }
              for (int i = 0; i < IMDB_Rating.size(); i++) {
                  if (!IMDB_Rating.get(i).equals("")){
                      rating.add(Float.valueOf(IMDB_Rating.get(i)));
                  }else rating.add((float) 0);
              }
              for (int i = 0; i < Genre.size(); i++) {
                  if (!Genre.get(i).equals("")){
                      if (Genre.get(i).charAt(0) == '"') {
                          a = Genre.get(i).substring(1, Genre.get(i).length() - 1);
                          b = a.split(", ");
                          for (int j = 0; j < b.length; j++) {
                              if (Objects.equals(b[j], genre)) {
                                  if (rating.get(i) >= min_rating) {
                                      if (runtime.get(i) <= max_runtime) {
                                          movies.add(name.get(i));
                                      }
                                  }
                              }
                          }
                      } else {
                          if (Objects.equals(Genre.get(i), genre)) {
                              if (rating.get(i) >= min_rating) {
                                  if (runtime.get(i) <= max_runtime) {
                                      movies.add(name.get(i));
                                  }
                              }
                          }
                      }
                  }
              }
              for (int i = 0; i <movies.size() ; i++) {
                  if (movies.get(i).equals("")){
                      movies.remove(movies.get(i));
                  }
                  if (movies.get(i).charAt(0)=='"'){
                      String m = movies.get(i).substring(1, movies.get(i).length() - 1);
                      movies.remove(movies.get(i));
                      movies.add(m);
                  }
             }
              movies.sort(Comparator.naturalOrder());
//         for (int i = 0; i < movies.size(); i++) {
//             System.out.println(movies.get(i));
//         }
              return movies;
          }


//    public static void main(String[] args) throws IOException {
//        new MovieAnalyzer("D:\\JAVA2\\homework\\A1\\A1_Sample\\resources\\imdb_top_500.csv")
////                .getCoStarCount();
////        getMovieCountByYear();
////        getMovieCountByGenre();
////        searchMovies("Adventure", 8.0f, 150);
////                .getTopMovies(20, "runtime");
////        getTopStars(15, "rating");
////
//    }
}