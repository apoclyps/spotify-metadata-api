package com.mixtape.spotify.api;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertNotNull;

/**
 * User: hector
 * Date: 19/02/13
 * Time: 23:22
 */
public class SpotifyMetadataTest {

    @Test
    public void testAlbumRequest() throws IOException {
        SpotifyMetadata spotifyMetadata = new SpotifyMetadata();
        assertNotNull(spotifyMetadata.search("foo", RequestType.album));
    }

    @Test
    public void testTrackRequest() throws IOException {
        SpotifyMetadata spotifyMetadata = new SpotifyMetadata();
        assertNotNull(spotifyMetadata.search("foo", RequestType.track));
    }

    @Test
    public void testArtistRequest() throws IOException {
        SpotifyMetadata spotifyMetadata = new SpotifyMetadata();
        assertNotNull(spotifyMetadata.search("foo", RequestType.artist));
    }

    @Test
    public void testAlbumUtf8() throws IOException {
        String album = "濱田 庄司";
        SpotifyMetadata spotifyMetadata = new SpotifyMetadata();
        assertNotNull(spotifyMetadata.search(album, RequestType.artist));
    }


}
