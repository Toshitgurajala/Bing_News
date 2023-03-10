package com.example.bingnews;

import java.util.List;

public class DataModel {
    private int totalEstimatedMatches;
    private List<NewsArticle> value;

    public int getTotalEstimatedMatches() {
        return totalEstimatedMatches;
    }

    public List<NewsArticle> getValue() {
        return value;
    }

    public class NewsArticle {
        private String name;
        private String url;
        private Image image;
        private String description;
        private String datePublished;
        private List<Provider> provider;

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public Image getImage() {
            return image;
        }

        public String getDescription() {
            return description;
        }

        public String getDatePublished() {
            return datePublished;
        }
        public List<Provider> getProvider() {
            return provider;
        }

        public class Provider {
            private String _type;
            private String name;

            public String get_type() {
                return _type;
            }

            public String getName() {
                return name;
            }
        }
    }

public class Image
{
    private  Thumbnail thumbnail;

    public Thumbnail getThumbnail() {
        return thumbnail;
    }
}
public class Thumbnail
{
    private  String contentUrl;
    private int width;
    private int height;
    public String getContentUrl() {
        return contentUrl;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}

}
