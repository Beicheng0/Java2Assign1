import java.io.*;
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
    File src = new File(dataset_path);
    BufferedReader is = null;
    try {
      is = new BufferedReader(new FileReader(src));
      String a;
      is.readLine();
      while ((a =is.readLine())!= null){
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
    }finally {
      is.close();
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
            CoStar.add(Star1.get(i));CoStar.add(Star2.get(i));CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);CoStar = new ArrayList<>();
            CoStar.add(Star1.get(i));CoStar.add(Star3.get(i));CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);CoStar = new ArrayList<>();
            CoStar.add(Star1.get(i));CoStar.add(Star4.get(i));CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);CoStar = new ArrayList<>();
            CoStar.add(Star2.get(i));CoStar.add(Star3.get(i));CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);CoStar = new ArrayList<>();
            CoStar.add(Star2.get(i));CoStar.add(Star4.get(i));CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);CoStar = new ArrayList<>();
            CoStar.add(Star3.get(i));CoStar.add(Star4.get(i));CoStar.sort(Comparator.naturalOrder());sumStar.add(CoStar);CoStar = new ArrayList<>();
        }
        for (int i = 0; i < sumStar.size(); i++) {
            for (int j = i + 1; j <sumStar.size() - i ; j++) {
                if (Objects.equals(sumStar.get(i).get(0), sumStar.get(j).get(0)) && Objects.equals(sumStar.get(i).get(1), sumStar.get(j).get(1))){
                    sumStar.set(i,sumStar.get(j));
                }
            }
        }
        sumStar.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting())).entrySet().stream().sorted(Map.Entry.<List<String>, Long>comparingByValue().reversed()
                ).forEachOrdered(e ->{
                    CoStarCount.put(e.getKey(),e.getValue().intValue());
        });
        System.out.println(CoStarCount);
        return CoStarCount;
    }
  public  List<String> getTopMovies(int top_k, String by){
        List<String> TopMovies = new ArrayList<>();
        Map<String,Integer> movies = new LinkedHashMap<>();
        if (Objects.equals(by, "runtime")){
            String [] c;
            List<Integer> runtime = new ArrayList<>();
            for (int i = 0; i < Runtime.size(); i++) {
                c = Runtime.get(i).split(" ");
                runtime.add(Integer.valueOf(c[0]));
            }
            for (int i = 0; i < runtime.size(); i++) {
                movies.put(name.get(i),runtime.get(i) );
            }
            movies.entrySet().stream().sorted(Map.Entry.<String,Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
                    ).forEachOrdered(e->{
                        TopMovies.add(e.getKey());
            });
        }
        if (Objects.equals(by, "overview")){
            List<Integer>overview = new ArrayList<>();
            for (int i = 0; i < Overview.size(); i++) {
                int a = Overview.get(i).length();
                overview.add(a);
            }
            for (int i = 0; i < overview.size(); i++) {
                movies.put(name.get(i),overview.get(i) );
            }
            movies.entrySet().stream().sorted(Map.Entry.<String,Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
            ).forEachOrdered(e->{
                TopMovies.add(e.getKey());
            });
        }
        for (int i = 0; i < top_k; i++) {
            System.out.println(TopMovies.get(i));
        }
        return TopMovies.subList(0,top_k);
    }
  public  List<String> getTopStars(int top_k, String by) {
          List<String> TopStars = new ArrayList<>();
          if (Objects.equals(by, "rating")){
              Map<String, List<Float>> rating = new LinkedHashMap<>();
              Map<String, Float> rating1 = new LinkedHashMap<>();
              List<Float> Rating = new ArrayList<>();
              List<Float> Rating1 = new ArrayList<>();
              for (String s : IMDB_Rating) {
                  float a = Float.parseFloat(s);
                  Rating.add(a);
              }
              for (int i = 0; i < Star1.size(); i++) {
                  Rating1.add(Rating.get(i));
                  rating.put(Star1.get(i),Rating1 );
              }
              for (int i = 0; i < Star2.size(); i++) {
                  String key = Star2.get(i);
                  if (rating.containsKey(Star2.get(i))) {
                      List<Float> Rating2 = rating.get(key);
                      Rating2.add(Rating.get(i));
                      rating.replace(key, Rating2);
                  } else{
                      List<Float> Rating2 = new ArrayList<>();
                      Rating2.add(Rating.get(i));
                      rating.put(key,Rating2);
                  }
              }
              for (int i = 0; i < Star3.size(); i++) {
                  String key = Star3.get(i);
                  if (rating.containsKey(Star3.get(i))) {
                      List<Float> Rating2 = rating.get(key);
                      Rating2.add(Rating.get(i));
                      rating.replace(key, Rating2);
                  } else{
                      List<Float> Rating2 = new ArrayList<>();
                      Rating2.add(Rating.get(i));
                      rating.put(key,Rating2);
                  }
              }
              for (int i = 0; i < Star4.size(); i++) {
                  String key = Star4.get(i);
                  if (rating.containsKey(Star4.get(i))) {
                      List<Float> Rating2 = rating.get(key);
                      Rating2.add(Rating.get(i));
                      rating.replace(key, Rating2);
                  } else{
                      List<Float> Rating2 = new ArrayList<>();
                      Rating2.add(Rating.get(i));
                      rating.put(key,Rating2);
                  }
              }
              for (String key:rating.keySet()){
                  List<Float> a = rating.get(key);
                  float sum = 0;
                  for (Float aFloat : a) {
                      sum += aFloat;
                  }
                  float r = sum / a.size();
                  rating1.put(key,r);
              }
              rating1.entrySet().stream().sorted(Map.Entry.<String,Float>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
              ).forEachOrdered(e->{
                  TopStars.add(e.getKey());
              });
          }
          if (Objects.equals(by, "gross")){
              Map<String, List<Integer>> gross = new LinkedHashMap<>();
              Map<String, Integer> gross1 = new LinkedHashMap<>();
              List<Integer> Gross1 = new ArrayList<>();
              List<Integer> Gross2 = new ArrayList<>();
              for (int i = 0; i < Gross.size(); i++) {
                  if (!Objects.equals(Gross.get(i), "")){
                      String[]a = Gross.get(i).split(",");
                      String b = "";
                      for (int j = 0; j < a.length; j++) {
                          b=b.concat(a[j]);
                      }
                      String c = b.substring(1,b.length()-1);
                      Gross1.add(Integer.valueOf(c));
                  }else Gross1.add(0);
              }
              for (int i = 0; i < Star1.size(); i++) {
                 if (!Objects.equals(Gross.get(i), "")){
                     Gross2.add(Gross1.get(i));
                     gross.put(Star1.get(i),Gross2 );
                 }
              }
              for (int i = 0; i < Star2.size(); i++) {
                  if (!Objects.equals(Gross.get(i), "")){
                      String key = Star2.get(i);
                      if (gross.containsKey(Star2.get(i))) {
                          List<Integer> Rating2 = gross.get(key);
                          Rating2.add(Gross1.get(i));
                          gross.replace(key, Rating2);
                      } else{
                          List<Integer> Rating2 = new ArrayList<>();
                          Rating2.add(Gross1.get(i));
                          gross.put(key,Rating2);
                      }
                  }
              }
              for (int i = 0; i < Star3.size(); i++) {
                  if (!Objects.equals(Gross.get(i), "")){
                      String key = Star3.get(i);
                      if (gross.containsKey(Star3.get(i))) {
                          List<Integer> Rating2 = gross.get(key);
                          Rating2.add(Gross1.get(i));
                          gross.replace(key, Rating2);
                      } else{
                          List<Integer> Rating2 = new ArrayList<>();
                          Rating2.add(Gross1.get(i));
                          gross.put(key,Rating2);
                      }
                  }
              }
              for (int i = 0; i < Star4.size(); i++) {
                  if (!Objects.equals(Gross.get(i), "")){
                      String key = Star4.get(i);
                      if (gross.containsKey(Star4.get(i))) {
                          List<Integer> Rating2 = gross.get(key);
                          Rating2.add(Gross1.get(i));
                          gross.replace(key, Rating2);
                      } else{
                          List<Integer> Rating2 = new ArrayList<>();
                          Rating2.add(Gross1.get(i));
                          gross.put(key,Rating2);
                      }
                  }
              }
              for (String key:gross.keySet()){
                  List<Integer> a = gross.get(key);
                  int sum = 0;
                  for (Integer aFloat : a) {
                      sum += aFloat;
                  }
                  int r = sum / a.size();
                  gross1.put(key,r);
              }
              gross1.entrySet().stream().sorted(Map.Entry.<String,Integer>comparingByValue().reversed().thenComparing(Map.Entry.comparingByKey())
              ).forEachOrdered(e->{
                  TopStars.add(e.getKey());
              });
          }
//          for (int i = 0; i < top_k; i++) {
//              System.out.println(TopStars.get(i));
//          }
          return TopStars.subList(0,top_k);
      }
  public  List<String> searchMovies (String genre,float min_rating, int max_runtime){
              List<String> movies = new ArrayList<>();
              String a;
              String[] b;
              String[] c;
              List<Integer> runtime = new ArrayList<>();
              for (int i = 0; i < Runtime.size(); i++) {
                  c = Runtime.get(i).split(" ");
                  runtime.add(Integer.valueOf(c[0]));
              }
              for (int i = 0; i < Genre.size(); i++) {
                  if (Genre.get(i).charAt(0) == '"') {
                      a = Genre.get(i).substring(1, Genre.get(i).length() - 1);
                      b = a.split(", ");
                      for (int j = 0; j < b.length; j++) {
                          if (Objects.equals(b[j], genre)) {
                              if (Float.parseFloat(IMDB_Rating.get(i)) >= min_rating) {
                                  if (runtime.get(i) <= max_runtime) {
                                      movies.add(name.get(i));
                                  }
                              }
                          }
                      }
                  } else {
                      if (Objects.equals(Genre.get(i), genre)) {
                          if (Float.parseFloat(IMDB_Rating.get(i)) >= min_rating) {
                              if (runtime.get(i) <= max_runtime) {
                                  movies.add(name.get(i));
                              }
                          }
                      }
                  }
              }
              movies.sort(Comparator.naturalOrder());
//         for (int i = 0; i < movies.size(); i++) {
//             System.out.println(movies.get(i));
//         }
              return movies;
          }


//    public static void main(String[] args) throws IOException {
//        new MovieAnalyzer("D:\\JAVA2\\homework\\A1\\A1_Sample\\resources\\imdb_top_500.csv");
//        getMovieCountByYear();
//        getMovieCountByGenre();
//        getCoStarCount();
//        getTopMovies(20, "runtime");
//        getTopStars(15, "gross");
//        searchMovies("Sci-Fi", 8.2f, 200);
//
//    }
}