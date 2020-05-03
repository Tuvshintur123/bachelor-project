public class Test {
    public static void main(String[] args) throws Exception {
        RequestBuilder builder = new RequestBuilder("65e8b024c3f1432381d1f0a6560bcec1", "8ee216f7d9064696af2476153c555e83");

        String apiUrl = builder.buildFoodGetUrl(4501L);
//
        System.out.println(apiUrl);
    }
}
