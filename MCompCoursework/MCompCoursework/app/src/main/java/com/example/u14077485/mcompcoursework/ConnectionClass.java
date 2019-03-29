package com.example.u14077485.mcompcoursework;

/**
 * Created by u14077485 on 15/03/19.
 */

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ConnectionClass {
    private String TAG = null;

    public String connectionCode(String tag) {
        String urlFeed = "https://at-web2.comp.glam.ac.uk/students/14077485/books."+tag;
        TAG = tag;
        String result = null;
        try {
            URL url = new URL(urlFeed);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;

            int responseCode = httpConnection.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK) {
                InputStream in = httpConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();

                String line = null;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    sb.append(line);
                }
                result = sb.toString();
            }
            httpConnection.disconnect();
        }
        catch (MalformedURLException e) {
            Log.e("CONNECTION_ERROR", "Malformed URL Exception", e);
        }
        catch(IOException e) {
            Log.e("CONNECTION_ERROR", "IO Exception", e);
        }
        return result;
    }


    private List<Book> parseJSON(String result) throws IOException {
        List<Book> books = new ArrayList<Book>();
        try {
            Log.d("Result", result);
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArray = jsonObject.getJSONArray("books");

            for (int i=0; i < jsonArray.length(); i++) {
                String title = "NA";
                String year = "NA";
                String publisher = "NA";
                double price = -1.0;
                List<Author> authors = new ArrayList<>();
                String imageURL = null;

                JSONObject object = jsonArray.getJSONObject(i);
                if(object.has("title")) {
                    title = object.getString("title");
                }
                if(object.has("year")) {
                    year = object.getString("year");
                }
                if(object.has("publisher")) {
                    publisher = object.getString("publisher");
                }
                if(object.has("price")) {
                    price = object.getDouble("price");
                }
                if(object.has("authors")) {
                    JSONArray authorArray = object.getJSONArray("authors");
                    for (int j = 0; j < authorArray.length(); j++) {
                        JSONObject author = authorArray.getJSONObject(j);
                        String firstName = "NA";
                        String lastName = "NA";
                        if (author.has("first")) {
                            firstName = author.getString("first");
                        }
                        if(author.has("last")) {
                            lastName = author.getString("last");
                        }

                        authors.add(new Author(firstName, lastName));
                    }
                }
                if(object.has("image")) {
                    imageURL = object.getString("image");
                }

                books.add(new Book(title, year, publisher, price, authors, imageURL));
            }

        }catch (JSONException e) {
            Log.e("JSONException","JSON Error", e);
        }
        return books;
    }

    private List<Book> parseXML(String result) throws IOException {
        List<Book> books = new ArrayList<>();
        String tag = null;
        String text = null;
        String title = null;
        String year = null;
        String publisher = null;
        double price = -1.0;
        List<Author> authors = new ArrayList<>();
        String firstName = null;
        String lastName = null;
        String imageURL = null;

        try {
            XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
            xmlPullParserFactory.setNamespaceAware(true);
            XmlPullParser xmlPullParser = xmlPullParserFactory.newPullParser();
            xmlPullParser.setInput(new StringReader(result));

            int eventType = xmlPullParser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType){
                    case XmlPullParser.START_TAG:
                        tag = xmlPullParser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        text = xmlPullParser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xmlPullParser.getName();
                        switch(tag){
                            case "book":
                                books.add(new Book(title, year, publisher, price, authors, imageURL));
                                // Reset values
                                authors = new ArrayList<>();
                                title = null;
                                year = null;
                                publisher = null;
                                price = -1.0;
                                firstName = null;
                                lastName = null;
                                imageURL = null;
                                break;
                            case "title":
                                title = text;
                                break;
                            case "year":
                                year = text;
                                break;
                            case "publisher":
                                publisher = text;
                                break;
                            case "price":
                                price = Double.parseDouble(text);
                                break;
                            case "author":
                                authors.add(new Author(firstName, lastName));
                                break;
                            case "first":
                                firstName = text;
                                break;
                            case "last":
                                lastName = text;
                                break;
                            case "image":
                                imageURL = text;
                                break;
                        }break;
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            Log.e("Connection class", "XML Parser Exception", e);
        }
        return books;
    }
    public List<Book> parseText(String result) {
        List<Book> books = new ArrayList<>();
        try {
            switch (TAG) {
                case "json":
                    books = parseJSON(result);
                    break;
                case "xml":
                    books = parseXML(result);
                    break;
                default:
                    Log.e(this.getClass().toString(), "Error in choosing parsing");
            }
        } catch (Exception e){
            Log.e("Connection class","Exception",e);
        }
        return books;
    }
}

