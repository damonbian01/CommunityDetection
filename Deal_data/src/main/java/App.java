/**
 * Created by ouxuan on 2017/6/2.
 */
public class App {
    public static void main(String[] args) {

	if (args.length < 2) {
		System.out.println("invalid arguments");
		System.exit(-1);
	}

        CIKM_data c = new CIKM_data();
        String input = args[0];
        String output = args[1]; 
        c.new_data(input, output);
    }
}
