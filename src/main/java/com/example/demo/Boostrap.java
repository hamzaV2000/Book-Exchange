package com.example.demo;

import com.example.entity.Book;
import com.example.entity.Review;
import com.example.entity.User;
import com.example.repositories.BookRepository;
import com.example.repositories.ReviewRepository;
import com.example.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@Transactional
public class Boostrap implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepository userRepository;

    private final BookRepository bookRepository;

    private final ReviewRepository reviewRepository;

    public Boostrap(UserRepository userRepository, BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
         /*
         System.out.println("saving users");
         userRepository.saveAll(getUsers());
         System.out.println("saving users");
              System.out.println("saving books");

                 try {
                     List<Book> list = getBooks();
                     PrintWriter rt= new PrintWriter(System.out);
                     int batchSize = 100000;
                     int totalObjects = 452114;
                     for(int i = 0; i < totalObjects ; i += batchSize){
                         rt.append("\nsublist ");
                         rt.append(Integer.toString(i));
                         rt.flush();
                         if( i+ batchSize > totalObjects){
                             List<Book> books1 = list.subList(i, totalObjects - 1);
                             bookRepository.saveAll(books1);
                             break;
                         }
                         List<Book> books1 = list.subList(i, i + batchSize);
                         bookRepository.saveAll(books1);
                     }
                     rt.close();

                 } catch (FileNotFoundException e) {
                     throw new RuntimeException(e);
                 }

         System.out.println("finished saving books");
         */

        System.out.println("Getting reviews ....");
        List<Review> list = null;
        try {
            list = getReviews();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("list arrived....");
        PrintWriter rt = new PrintWriter(System.out);
        int batchSize = 1000;
        int totalObjects = list.size();
        for (int i = 0; i < totalObjects; i += batchSize) {
            rt.append("\nsublist ");
            rt.append(Integer.toString(i));
            rt.flush();
            if (i + batchSize > totalObjects) {
                List<Review> review1 = list.subList(i, totalObjects - 1);
                reviewRepository.saveAll(review1);
                break;
            }
            List<Review> review1 = list.subList(i, i + batchSize);
            reviewRepository.saveAll(review1);
        }
        rt.close();
        System.out.println("finished saving....");
    }

    private List<User> getUsers() {
        LinkedList<User> users = new LinkedList<>();
        String line = "";
        String splitBy = ",";
        PrintWriter rt = new PrintWriter(System.out);
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/users_beta.csv"));
            br.readLine();
            Integer count = 0;

            while ((line = br.readLine()) != null)   //returns a Boolean value
            {
                rt.append(count.toString());
                rt.append('\n');
                rt.flush();
                count++;
                String[] csvUser = line.split(splitBy);    // use comma as separator
                String firstname = csvUser[0];
                String lastName = csvUser[1];
                String gender = csvUser[2];
                String streetAddress = csvUser[3];
                String city = csvUser[4];
                String email = csvUser[5];
                String userName = csvUser[6];
                String password = csvUser[7];
                String telephone = csvUser[8];


                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

                String str[] = csvUser[9].split("/");
                String month = str[0];
                String day = str[1];

                if (Integer.valueOf(day) < 10)
                    day = "0" + day;

                if (Integer.valueOf(month) < 10)
                    month = "0" + month;
                LocalDate birthDate = LocalDate.parse(month + "/" + day + "/" + str[2], formatter);
                LocalDate joinDate = LocalDate.now();
                LocalDate lastLoginDate = LocalDate.now();

                User user = new User();
                user.setFirstName(firstname);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setCity(city);
                user.setGender(gender);
                user.setUserName(userName);
                user.setStreetAddress(streetAddress);
                user.setPassword(password);
                user.setTelephone(telephone);
                user.setBirthDate(birthDate);
                user.setJoinDate(joinDate);
                user.setLastLoginDate(lastLoginDate);
                users.add(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        rt.close();
        return users;
    }

    private List<Book> getBooks() throws FileNotFoundException {
        // Set<Integer> set = new HashSet<>();
        LinkedList<Book> books = new LinkedList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/books_with_authors_names.csv"));
                CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);
        ) {
            for (CSVRecord record : parser.getRecords()) {
                Book book = new Book();
                book.setId(Long.valueOf(record.get("book_id")));
                book.setDescription(record.get("correct_book_description"));
                book.setTitle(record.get("title_without_series"));
                book.setPublicationYear(Float.parseFloat(record.get("publication_year")));
                book.setPublisher(record.get("publisher"));
                book.setRatingCount(Integer.valueOf(record.get("ratings_count")));
                book.setBook_average_rating(Float.parseFloat(record.get("book_average_rating")));
                book.setCoverPage(record.get("cover_page"));
                book.setBookUrl(record.get("book_url"));
                book.setNumOfPages(Float.parseFloat(record.get("num_pages")));
                book.setGenres(record.get("genres"));
                book.setAuthor(record.get("name"));
                books.add(book);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(books.size());
        return books;
    }

    private List<Review> getReviews() throws IOException {
        List<Review> list = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("src/main/java/com/example/betaReviews.csv"));
        CSVParser parser = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(br);

        int i = 0;
        PrintWriter rt = new PrintWriter(System.out);
        Random random = new Random();
        for (CSVRecord record : parser.getRecords()) {
            rt.append("\n");
            rt.append(Integer.toString(i++));
            rt.flush();
            Review review = new Review();
            review.setBook(bookRepository.getById(Long.valueOf(record.get("book_id"))));
            review.setUser(userRepository.getById(Long.valueOf(record.get("new_id"))));
            review.setReviewText(record.get("review_text"));
            review.setUserRating(Byte.valueOf(record.get("user_rating")));
            LocalDate date = LocalDate.now().minusYears(random.nextInt(4)).minusMonths(random.nextInt(12)).minusDays(random.nextInt(12));
            review.setDate(date);

            list.add(review);
        }
        System.out.println("list leaving....");
        return list;
    }
}

