package com.example.demo.bilibili;

import java.sql.*;

public class GenerateHtmlPlugin {
    public static void main(String[] args) throws Exception {
        generate_html();
    }

    private static String generate_html() throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" <style>\n" +
                ".group\n" +
                "{\n" +
                "\tfloat:left;\n" +
                "}\n" +
                "</style>");
        stringBuilder.append("<body>");

        try (
                Connection connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "1");
                final Statement statement = connection.createStatement()
        ) {
            final String sql = "\n" +
                    "select  distinct a.C2CITEMSID,a.C2CITEMSNAME,a.SHOWPRICE,a.SHOWMARKETPRICE,img,temp.minPrice from GENERIC a, (select   C2CITEMSNAME , min(SHOWPRICE) as minPrice from GENERIC group by C2CITEMSNAME ) temp\n" +
                    "                                                                       ,GENERIC_DETAILDTOLIST\n" +
                    "                                                         where temp.C2CITEMSNAME=a.C2CITEMSNAME\n" +
                    "                                                           and GENERIC_DETAILDTOLIST.GENERIC_ID=a.ID\n" +
                    "                                                           and a.SHOWPRICE=temp.minPrice order by temp.minPrice desc ";
            final ResultSet resultSet = statement.executeQuery(" \n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "select   C2CITEMSID, a.C2CITEMSNAME, a.SHOWPRICE,a.SHOWMARKETPRICE,(SELECT MAX(IMG)   from GENERIC_DETAILDTOLIST where GENERIC_ID=a.ID) as IMG from GENERIC a\n" +
                    "   , (select  trim(a.C2CITEMSNAME) as C2CITEMSNAME,a.SHOWPRICE,MAX(a.C2CITEMSID) as C2CITEMID\n" +
                    "\n" +
                    "                           from GENERIC a\n" +
                    "                              , (select C2CITEMSNAME, min(SHOWPRICE) as minPrice\n" +
                    "                                 from GENERIC\n" +
                    "                                 group by C2CITEMSNAME) temp\n" +
                    "                              , GENERIC_DETAILDTOLIST\n" +
                    "                           where temp.C2CITEMSNAME = a.C2CITEMSNAME\n" +
                    "                             and GENERIC_DETAILDTOLIST.GENERIC_ID = a.ID\n" +
                    "                             and a.SHOWPRICE = temp.minPrice\n" +
//                    "                             and   SHOWPRICE*100/SHOWMARKETPRICE between 20 and 50 " +
                    "                            and ( a.C2CITEMSNAME like '%TAITO%' or a.C2CITEMSNAME like '%TAITO%')\n" +
//                    " and a.SHOWPRICE  between 100 and 600 " +
//                    " and a.SHOWPRICE  >100 " +
//                    "AND a.UID='26***0' and a.UNAME='b***' AND a.UFACE='https://i1.hdslb.com/bfs/face/2ea48351d7c1566c63bec26cdf37b86c28c2c911.webp' "+
//                    "and a.CREATION_TIME > '2024-04-25 12:02:00' " +

                    "                           group by a.C2CITEMSNAME, a.SHOWPRICE ) temp\n" +
                    "\n" +
                    "where temp.C2CITEMID = a.C2CITEMSID\n" +
                    "and temp.C2CITEMSNAME=a.C2CITEMSNAME\n" +
                    "and temp.SHOWPRICE=a.SHOWPRICE\n" +
                    "order by  SHOWPRICE ASC;" +
                    "");
            while (resultSet.next()) {
                final String C2CITEMSID = resultSet.getString("C2CITEMSID");
                final String C2CITEMSNAME = resultSet.getString("C2CITEMSNAME");
                final String SHOWPRICE = resultSet.getString("SHOWPRICE");
                final String SHOWMARKETPRICE = resultSet.getString("SHOWMARKETPRICE");
                final String IMG = resultSet.getString("IMG");
                stringBuilder.append("<div class='group'>");
                stringBuilder.append("<img data-v-0bc4c2fb=\"\" src=\"https:" +
                        IMG +
                        "\" \n" +
                        "\t\t   style=\"width: 20rem; height: 20rem;\"> \n" +
                        "\t\t   \n" +
                        "\t\t   <div class=\"goods-name text-13 text-[#212121] truncate\">" +
                        "<a  style=\"font-size: xx-small\"  href=\"https://mall.bilibili.com/neul-next/index.html?page=magic-market_detail&noTitleBar=1&itemsId=" +
                        C2CITEMSID +
                        "&from=market_index\" > " +
                        C2CITEMSNAME +
                        "</a>" +
                        "</div>\n" +
                        "\t\t   <div class=\"goods-price text-16 font-medium text-[#786DF6]\"><span><span class=\"text-11\">¥</span>" +
                        SHOWPRICE +
                        "</span>\n" +
                        "\t\t   <span class=\"ml-4 text-12 text-[#9499A0] line-through\"><span class=\"text-11\">¥</span>" +
                        SHOWMARKETPRICE +
                        "</span>\n" +
                        "\t\t\t</div>\n" +
                        "\t\t\t</div>");

            }
            stringBuilder.append("</body>");

        }

        stringBuilder.append("</body>");

        return stringBuilder.toString();
    }
}
