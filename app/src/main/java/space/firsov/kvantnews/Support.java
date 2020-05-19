package space.firsov.kvantnews;

public class Support {
    public String answer, question;
    public long id;

    Support(long id, String answer, String question) {
        this.id = id;
        this.answer = answer;
        this.question = question;
    }
}
