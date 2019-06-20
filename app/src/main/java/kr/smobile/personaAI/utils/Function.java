package kr.smobile.personaAI.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.TypedValue;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Function {

    public static String createEventDetailsDateTime(String evestart) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = simpleDateFormat.parse(evestart);


            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);

            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String dividePhoneNumber(String phone) {
        int k = 0;
        StringBuilder phonePattern = new StringBuilder();
        for (int i = 0; i < phone.length(); i++) {
            phonePattern.append(phone.charAt(i));
            if (k == 2 || k == 6) {
                phonePattern.append("-");
            }
            k++;
        }
        return phonePattern.toString();
    }

    public static Date getDate(String evestart) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        Date date = new Date();
        try {
            date = simpleDateFormat.parse(evestart);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return date;
    }

    public static String createEventDetailsDateTime(long evestart) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);

        return simpleDateFormat.format(DateUtils.clearDate(evestart));

    }

    public static String createEventDetailsDate(long evestart) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);

        return simpleDateFormat.format(DateUtils.clearDate(evestart));

    }


    public static String getDifferenceDate(String evestart, String eveend) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date start = simpleDateFormat.parse(evestart);
            Date end = simpleDateFormat.parse(eveend);


            long difference = Math.abs(end.getTime() - start.getTime());
            long differenceDates = (difference / (24 * 60 * 60 * 1000)) + 1;

            //Convert long to String
            String dayDifference = Long.toString(differenceDates);
            Log.d("dayDifference", dayDifference);
            return dayDifference;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    public static String createTime(String from_time) {
        if (from_time != null) {
            String hour = from_time.substring(0, 2);
            String minute = from_time.substring(2, 4);
            String second = "00";
            if (from_time.length() > 4) {
                second = from_time.substring(4);
            }
            return hour + ":" + minute + ":" + second;
        } else {
            return "";
        }
    }

    public static String createMeetupDateTile(String headerTitle) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date = simpleDateFormat.parse(headerTitle);


            simpleDateFormat = new SimpleDateFormat("MM.dd EEE", Locale.KOREA);

            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return headerTitle;
    }

    public static String createOrderDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = simpleDateFormat.parse(dateString);


            simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.KOREA);

            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static String createPointDate(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date date = simpleDateFormat.parse(dateString);


            simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);

            return simpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateString;
    }

    public static int dp2px(int dp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static String convertArrayToStringWithComma(String[] categories) {
        String withComma = "";
        for (int i = 0; i < categories.length; i++) {
            if (i == 0) {
                withComma += categories[i];
            } else {
                withComma += "," + categories[i];
            }
        }

        return withComma.length() > 0 ? withComma : null;
    }

    public static String createDiscountValue(String s) {
        return s + "%";
    }

    public static String createPointText(String s) {
        String format = "";
        if (s != null) {

            if (s.startsWith("-")) {
                if (s.length() < 5) {
                    format = s;
                } else if (s.length() < 8) {
                    format = s.substring(0, s.length() - 4) + "," + s.substring(s.length() - 3, s.length());
                } else {
                    format = s;
                }
            } else {
                if (s.length() < 4) {
                    format = s;
                } else if (s.length() < 7) {
                    format = s.substring(0, s.length() - 3) + "," + s.substring(s.length() - 3, s.length());
                } else {
                    format = s;
                }
            }
        }
        return format;
    }

    public static String dataCheck(Object s) {
        if (s == null) {
            return "";
        }
        if ((s instanceof String) && (((String) s).trim().length() == 0)) {
            return "";
        }
        return String.valueOf(s);
    }

    public static String dataCheckSpec(Object s) {
        if (s == null) {
            return "-";
        }
        if ((s instanceof String) && (((String) s).trim().length() == 0)) {
            return "-";
        }
        return String.valueOf(s);
    }

    public static String createPrice(Long integer) {
        if (integer != null) {
            DecimalFormat format = new DecimalFormat("###,###,###");
            return format.format(integer);
        } else {
            return "";
        }
    }

    public static String createPrice(Double integer) {
        if (integer != null) {
            DecimalFormat format = new DecimalFormat("###,###,###");
            return format.format(integer);
        } else {
            return "";
        }
    }

    public static String createPrice(String price) {
        if (price != null) {
            DecimalFormat format = new DecimalFormat("###,###,###");
            return format.format(price);
        } else {
            return "";
        }
    }

    public static String createPrice(Integer integer) {
        if (integer != null) {
            DecimalFormat format = new DecimalFormat("###,###,###");
            return format.format(integer);
        } else {
            return "";
        }
    }

    public static boolean checkNetworkConnection(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String hash512(String data) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.update(data.getBytes());
        return bytesToHex(md.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes)
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }


    public static boolean isValidEmail(String email) {
        boolean err = false;
        String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(email);
        if (m.matches()) {
            err = true;
        }
        return err;
    }

    public static String createImageUrl(String titleImage, String size) throws UnsupportedEncodingException {
        //return BuildConfig.BASE_IMAGE_URL + URLEncoder.encode(titleImage, "UTF-8") + "?size=" + size;
        return "";
    }

    public static String nowAsISO() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String nowAsISO = df.format(new Date());
        return nowAsISO;
    }

    public static int getResponseCode(Throwable throwable) {
        int code = 0;
        if (throwable instanceof HttpException) {
            code = ((HttpException) throwable).code();
        }
        return code;
    }
}
