package com.mixtape.spotify.api;


import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static junit.framework.Assert.*;


public class ResponseParserTest {


    @Test
    public void testArtistParsing() throws IOException {
        String jsonBlob = readFile("/artists.json");
        Response response = ResponseParser.parse(jsonBlob);

        assertEquals(response.getInfo().getLimit(), 100);
        assertEquals(response.getInfo().getOffset(), 0);
        assertEquals(response.getInfo().getQuery(), "foo");
        assertEquals(response.getInfo().getPage(), 1);
        assertEquals(response.getInfo().getNumResults(), 26);
        assertEquals(response.getInfo().getType(), RequestType.artist);

        assertEquals(26, response.getArtists().size());

        Artist artist = response.getArtists().iterator().next();
        assertEquals(artist.getHref(), "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD");
        assertEquals(artist.getName(), "Foo Fighters");
        assertEquals(artist.getPopularity(), 0.65162, 0.0001);

    }

    @Test
    public void testAlbumParsing() throws IOException {
        String jsonBlob = readFile("/albums.json");
        Response response = ResponseParser.parse(jsonBlob);

        assertEquals(response.getInfo().getLimit(), 100);
        assertEquals(response.getInfo().getOffset(), 0);
        assertEquals(response.getInfo().getQuery(), "foo");
        assertEquals(response.getInfo().getPage(), 1);
        assertEquals(response.getInfo().getNumResults(), 200);
        assertEquals(response.getInfo().getType(), RequestType.album);

        assertEquals(100, response.getAlbums().size());

        Album album = response.getAlbums().iterator().next();
        assertEquals(album.getName(), "Greatest Hits");
        assertEquals(album.getPopularity(), 0.85017, 0.0001);
        assertEquals(album.getExternalIds().size(), 1);
        assertEquals(album.getArtists().size(), 1);
        assertEquals(album.getHref(), "spotify:album:1zCNrbPpz5OLSr6mSpPdKm");

        ExternalId externalId = album.getExternalIds().iterator().next();
        assertEquals(externalId.getType(), "upc");
        assertEquals(externalId.getId(), "884977373295");

        Artist artist = album.getArtists().iterator().next();
        assertEquals(artist.getName(), "Foo Fighters");
        assertEquals(artist.getHref(), "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD");
        assertNull(artist.getPopularity());

        Availability availability = album.getAvailability();
        assertNotNull(availability);
        assertEquals(availability.getTerritories().size(), 225);
    }

    @Test
    public void testTrackParsing() throws IOException {
        String jsonBlob = readFile("/tracks.json");
        Response response = ResponseParser.parse(jsonBlob);

        assertEquals(response.getInfo().getLimit(), 100);
        assertEquals(response.getInfo().getOffset(), 0);
        assertEquals(response.getInfo().getQuery(), "foo");
        assertEquals(response.getInfo().getPage(), 1);
        assertEquals(response.getInfo().getNumResults(), 2360);
        assertEquals(response.getInfo().getType(), RequestType.track);

        assertEquals(100, response.getTracks().size());

        Track track = response.getTracks().iterator().next();
        assertEquals(track.getName(), "Everlong");
        assertEquals(track.getPopularity(), 0.76197, 0.0001);
        assertEquals(track.getLength(), 250);
        assertEquals(track.getTrackNumber(), 3);

        assertEquals(track.getExternalIds().size(), 1);
        ExternalId externalId = track.getExternalIds().iterator().next();
        assertEquals(externalId.getType(), "isrc");
        assertEquals(externalId.getId(), "USRW29600011");

        Album album = track.getAlbum();
        assertEquals(album.getName(), "Greatest Hits");
        assertEquals(album.getHref(), "spotify:album:1zCNrbPpz5OLSr6mSpPdKm");
        assertEquals(album.getReleaseYear().intValue(), 1995);
        assertEquals(album.getAvailability().getTerritories().size(), 225);

        assertEquals(track.getArtists().size(), 1);
        Artist artist = track.getArtists().iterator().next();
        assertEquals(artist.getHref(), "spotify:artist:7jy3rLJdDQY21OgRLCZ9sD");
        assertEquals(artist.getName(), "Foo Fighters");

    }


    private String readFile(String file) throws IOException {
        BufferedReader stream = new BufferedReader(new InputStreamReader(ResponseParserTest.class.getResourceAsStream(file)));
        try {
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = stream.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        } finally {
            stream.close();
        }
    }


}
