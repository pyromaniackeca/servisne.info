(ns servisne-info.scrape.ns-rs
  (:require [net.cgrand.enlive-html :as en]
            [clojure.string :as s]))

(def info-site
  {:url "http://www.021.rs"
   :links-path "/Novi-Sad/Servisne-informacije/"})

(defn html-resource [url]
  (en/html-resource (java.net.URL. url)))

(defn path-to-url [site path]
  (str (:url site) path))

(defn info-links-url [site]
  (str (:url site) (:links-path site)))

(defn info-links-page [site]
  (html-resource (info-links-url site)))

(defn info-links [site html]
  (map
    (fn [a]
      (path-to-url site (:href (:attrs a))))
    (en/select
      html
      [:div.najava :div.leading :a])))

(defn info-page-title [html]
  (s/trim (first (:content (first (en/select html [:h1.contentheading]))))))

(defn info-page [html]
  {:title (info-page-title html)})
