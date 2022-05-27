package org.swasth.hcx.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.swasth.hcx.dto.Response;
import org.swasth.hcx.utils.JSONUtils;
import org.swasth.hcx.controllers.v1.*;
import org.swasth.hcx.helpers.EventGenerator;
import org.swasth.hcx.service.HeaderAuditService;
import org.swasth.hcx.utils.OnActionCall;

import java.util.HashMap;
import java.util.Map;


@WebMvcTest({CoverageEligibilityController.class, PreAuthController.class, ClaimsController.class, PaymentsController.class, AuditController.class, StatusController.class, SearchController.class, CommunicationController.class, PredeterminationController.class})
@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class BaseSpec {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @MockBean
    private OnActionCall onActionCall;

    @MockBean
    protected EventGenerator mockEventGenerator;

    @Mock
    protected Environment mockEnv;

    @MockBean
    protected HeaderAuditService headerAuditService;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public String getRequestBody() throws JsonProcessingException {
        Map<String,Object> obj = new HashMap<>();
        obj.put("payload","eyJ4LWhjeC1yZWNpcGllbnRfY29kZSI6IjEtMjk0ODJkZjMtZTg3NS00NWVmLWE0ZTktNTkyYjZmNTY1NzgyIiwieC1oY3gtY29ycmVsYXRpb25faWQiOiJhMWI1NDY4Ny1iOWYxLTQxODgtOTcxMS0xN2Q0ODg0MGIzOTAiLCJoLWhjeC10ZXN0X3JhbmRvbSI6InRydWUiLCJ4LWhjeC1zdGF0dXMiOiJyZXF1ZXN0LnF1ZXVlZCIsImgtaGN4LWFwaV9jYWxsX2lkIjoiZWNmM2IwNjYtNWRkZC00MGQxLTlhYmUtNzYzNzFmNTZkMzBlIiwieC1oY3gtcmVxdWVzdF9pZCI6IjA1OTAyMGM3LWVjOWEtNDNjMy04OGNiLTYzOTc5ZGIzZTU5ZCIsImgtaGN4LWNvcnJlbGF0aW9uX2lkIjoiZjZiNTc4ZDItN2NjMC00NTc4LTk4ZTctMDkxMjM4ZTRjNWMwIiwieC1oY3gtdGltZXN0YW1wIjoiMjAyMi0wNC0yMFQxMzoyNzozMi4wMTYrMDU6MzAiLCJ4LWhjeC1zZW5kZXJfY29kZSI6IjEtNTIxZWFlYzctOGNiOS00YjZjLThiNGUtNGRiYTMwMGFmNmY0IiwiZW5jIjoiQTI1NkdDTSIsIngtaGN4LXdvcmtmbG93X2lkIjoiMjljMDZlNjgtODNhOS00MzQwLWIwMDItYmEzYjhhZjZmZjlmIiwiYWxnIjoiUlNBLU9BRVAtMjU2IiwieC1oY3gtYXBpX2NhbGxfaWQiOiI5OWI5YTZkNi04ODA4LTRiNTMtYjYzNS1hNDhkYjgzZTM1ZGIiLCJ4LWhjeC10ZXN0X3JhbmRvbSI6InRydWUifQ.TkkixxViuT93qPFKvnIiQHyQLDuVps1e5AVuqFY--hyire6yLNg_LKQQlKJvp9a5o2XnqODrVWPUBWHYQB88nqlEZvxpJfqlW9ZjXyDGBLKf3885P1GaYJL3EJscFn-UCXIlQWU4xmqzI3C-7B4Yczx9FUPzRo9FgXlgKly4x4WGYrLi59rAuz60afZFo8lnLeBymvafiwfckjzDoV838Xti9FGOlQ4leNyyY8EwMnGljXk8zPspk87r67Bj_QTKhjMxQQ0TAvgvllNUoY0JF6GewD_-XAvxDH5BuihtHVOCjCNgZNsbAZHTw3hrvQAdXPHNCdtI6vIONMhdcWSo-w.26526NmL1ltgh4oz.EXe-VkATD3uLYnodGRcEY7VMy40JWKMwPPcFJy9RWxBQoWJ-yYqUxSm2sewKTmVCdwc40j--cLUmF5u8ovwpOJytFnY-FVAFOAxUIHVk2wcy5K0pQKd876wEx4sINzpTPVfCUre2uFDbPTCDZPLpa2uxc9e8rHmEzfLkR_h8UULrQ6PscLto_seG7_8Q6P1lb7FliHxh2ZWZZx_o1lGqDNd1-BTDzj5MnYepdcjX-40bQHJVcfyo1FygJc8P_D5GlLd19vwnF-o14Zl0-2xZtO2fWS-VgXSInhmT0JiUrlsGbVTB05taxHwvvy_JbBUxBjtntrQZ68QpiqfE0ZSk1uRFp1J8ve78pU-QDYpp46sh5JN-cAxAUmMkrY5Kda0Q3PTudpl4j2FDAHkr3AQlvYJL0T_earthDOOuc81Hu6ju9U_dS8PvvKTefGNANwNX1jsfG_Mby8jBhjNsuHYbCStUtinqgBIJXnUYb2NfH4IqC6BxNWxsKzoqMG5UM0wr3n4wVT3_2GNDKOBV4iNIsFzznWMY-sqD-q_ZK6NBjsfD23RbRQtacpFBYCW2LQxQZ4wrMvxmUYI56XJJUoDvpiggG5IcY4Y_t-260Et68K5X_eIrzYk8Up3bPONZiIWvj7jRfGDGh6Kmff0jWuqkF2egLih_s90DALHZ9cWVqeIFwMVzHvhA4FFWIRsz9mvLJC1iwegka4ipJMSkRJOIrEZ5drM8krUQ2cajYZkoFvnMH_N6o_9zmwYJPBq8CPy-t-sNBUSHnA-ielKXFASAui37mJGP_Svyf118Y8IdALmsFyGf0oyVHuxAiobxqnOgimpPxPtakXLFlv36V_MYBwVf6R-NkrtUcjU1YtMqxlV-rUpusOY2zvtMH7XNqG-sKszlgptaomWVorAPH6He1ONCtQ2rdAI1ORraFsQtP3xQ6b1YpMz0OC0mYWXtCdCbvFAKIjnUQQHiydBJWAcnfs7VFHMI1fW5WjJQC7u0Mfth8iWCttYD0EEouTwB4mWJ0K3AJW1Jvhjo9B-U3ev1UxNO6Ukr48P8qjkj7jTIB6JGEp4t14a7K_5rPrVUlDCKVCN6RqR0yC-tOSvjacIj9z17qToc_fBsluc8BcZ-I8OrY7s15Z_nwWZSM-t1synibca9_3pyYtx6qJL-zPUXK51j_v7CnNJirUnp_cF2P8RutJfG4AvFNK_fvE5hXKid1YXywrX7G7q8avsOwMxI05s2AW-UVTwb-sFAdB7sWKFlPAKQjcfIZm8_RXKMgVVQFYbP3ubCsvl6tXATRPY1iII5jsHYpcvVuY-hDpHfiLAle-jGFcLvrtyRdpY2IJq5wvk15X3Ey2nCIiddr0y2MftieB0y1_gnBeYXUJmRG3pkmQ6bQFyhKScYkX9u4g2ew7_vdD-6a8KcYqEaWEmaejqdocXNqxKr99H5EubdKUfQwmBogYetdbvR0yrCsheeLqJtRLFqsoe1nXGD2kPz1lCIwCz6bx_Yr3Q07N8-f3SxaB1X6-osgX4B50Q0yDtoNpGaHWHITyJnjFpkQ5nAx4oKR6pEtVO0_w8Cj2pxFbeAtlJVAtePOqUE0l-pEsrvvHCwOqld9oC2P0ksgF7JX2yMYiPKZs_YFtVUwEy7OA4nyfnKsb73pgY-UUHXfp6d-FQ19klU-VfzcLP_Y9Y1TEosWvuztDzjOkvizBd14zh4TsfkNtuWAeNE5ZQMQBlT4mcRrGqotnxEzzgj3AMLbpk0xvPl6H7j3eD7nonBvIGaDgKs9MjKr1K4rdWhlcQAvssiX64IbtAJEDgZ4ZYEwxz93j-e_A3xl2ly2FshW94lXrstSTb-ZUom7CPCQGLZv9tlH42xECFyy46cP7gcR4ebajvdxiAPPsSl7d2rsXgYanTRr71Hml2_RHX0mwVRmO7liQRizmcl1ELdf_V4QH8V4m8mjZO3dur1azTT24j_DdcdaN1Iwb4wXPX3QfmITd7hvVzbKzGNEmuq8gvTwGqeUGt_aNGOfdKk8uEiGOi3iYsjST1w-oga7FKSMxlJZN531tWoNgUo6iIpsRxUaVr5_wnuNbAqPUHOYTwb1SmmArb88FLjbM47vsM9NyGmYh_QLebhzKvbSsOxVr_elzf6-70aICT8MlPxXmiw4jtkmykNh4jbVfZL8qkJhHqGcHyCU5lWEFnE-EubgY53RCYhDQ6A1rf45XEaCmZ0siXZoPuQR5pU3qIoFNgR-m3yPkwKD83MwMWTxCg7FlnU3_7SFsSBS09y26uNWz3Ec6NYedCV42NKcG80EGBghC7CMPxHk_P4XzY1w5M_fzgcCIY3C57pGrH-h-omT4tdP2xvYWQS9eCafya-I19AuG2phza0aI244lOmIpBWmAVfxsKAeBwXYWO--4FuGp4mSg1p_0XxvMAezzR14cRdgZVoIn3Ey_Yo8HQleR_VInEWCuLlQBJdXUe8jtkK63wElXLNuSyhxVjqIjuc9sLMql5VBMZHi17I6z0YVewrcUJwBi-LN1Hv3Jf6JUqpgQmtazC8dM1bszf3dtTjJ2YwJStL3y66sRqwneLZU7sCW464W_G8l5PPZkEFaFP9gY_3Uc2fNeB7H1IXfBurRmyL5w75cQssJ1qGPxPBK3DrldliV9_RS6PKjS-RFg72XRQwRICtprS8pWGvitUsC4X8my_7SjUICmPybaUdneQdn1y41F3wVJLXGOeIEVT3m5Hoc27J736GX4eM3PygoSRJY_e1_kGn2af7iG9YUu8Q6ShygVZV-w5TFQMWhRI93AlEL6Vh8_5pyZTOdv9FHmpcjRsYlNvmi7kueP3ua6Uf-JfTKvMR3NIlU-sKZQGZhmFx644MDOm1boAhuqzFuwkSfXcartxW0iVv7Sx24qzoz9louzPoO30RrwoQ-dfJ5SdqdRK4JMUGocqx4eOyv0HbxTzbKRiT-rg4o5wsTPo5y2NKKfuMB1FsOisUF_SbbpEHlw8uJcx8VEtmM-Ru5QJzrtj9tNOxdT-y4LftDi4qPjXfu0K0k5cK0LlI0Z0sWPTFncHYSP5ChTi09wghRjc9P8hDnZtl_ba0B_STuyHhtiZGq8rLzfQ3IgIa64_ERGBeJ7xfO58OMtE2ZLHv-0w7MMcfz-7DvrDp3MU68cD2_utZQ1bD5q_w5_z2far0KPqLbG0rCe7mLsM5tWkRDF8DIQsLK0HDJg8ciFFoq-49hc8GpmKc4g4trGI4lExBVkYsdcztgLPKqLftAV0NPJ8RcatSxFu9t3icy7gSwhU0Q8CPBq1GSUl1a5wgann0NXJpDxC-76CEttay_7mILeuBZR5sdItADNkE1OtBJqJuYOtTEhoBJihsMokTdfpZAZF7Aibd3oVJ5RCKlyXst0mlIVdeGaFa36Ix9_kwAh_TU6Vcyr-L9JfjU5LlImUWFV25oXNpPdSvGR609iJo-70wKO3XzmLXtW9jXr5PvmToy2fo9872E4MB125KJlEI6HqIdVpYZl1L-6Nm7UuZBYLb8KSIpr9cC-KiBaHHCghUhbSlK6owWJcmjQXaIXcqz0hgdKJ0Jsg5RuEjCfXluIt1EryT9tUDoPgWgIL21FVY_rvZkGc_QZIqH50mWZxavo7wImEg5Owa70mxuAuYtLdZspjIcwEcmLEtakx47C-86cQ96M85Oy25e_ZAnaZi8i6YEUub4tN_VslCKoQ3iIh0L2COapOzSdPgHCWvdKtSEsbuuLl2pfh6y9eBeAzOZa2QNI8ino1xKqqnlQu88IvjRwYoRb8BDZ5xZmT0GqXiVGYDPLRl0-QZdalcDJ604Crgrqr0Y3wjC077GaOdGFZ0cOY2fxwYChHP_IaektNTDmo2slc_eh4RL5iQJMjGvYv0u9er5AFYaGXAReMw2Hy0DrVkclHDYSAQ0gQGaJ2_lc5zuFzr-u9N5IO7WGwyZrSriP29j4KNX8Kc-1ZE2zkBM0ONlqlyiTw7mJcIAHqQFykOCuTIir4BwvPwEn9uaYbfwpWK6c4gkYqZ4vfh0aNrGozc8A-LoJwvAPTe4ZK0qkMhDhersWlIOWj6jeoyaN4fHJNPD3dUxqr5M6t6h7NWP60TkBGlg4dgzMnE7dQrXmE9YylBZ3Rv6FzRRcl5n3L88OI6bRTGm830IXVskVjqpHB4Qss63uL2WfrknVbtK6gJoUSPYBiJhC0-wePcL3uCO2cNJFQdpkstX0axy8BrVfeTuWR8CtOcPWwFTaKL7RMqXuDWDPUR_PrVT5KN4-46WiPpS8imOxUbJI45mNKqIb9jKQQ8AfEDrMxq8kGDE4oPqeLG3uGZd5P5yTKBH1gLWuH17Ag8O3sjpeL-W1pknZa-Wn6068VJSDqka9oeQJx2ALYoZ7aKj7YX8M36uD5z1Rx2djIqeuUxV2ov81dJyi7Cp3OevMTVqxzqSvJ4gL4-g1uemWXfXwkfVOeNOMZNXxJRE8UsO490CpFDRiXt5f4FolcEOat96bsOehMshyvMNSuw0fNpK_IDN3nacpaG2dHeWsj1yUGq2Cyk5hmetBibAfOs0sggr3scKec929W1_2-krjlv_efZCGYb7UnkHOqV_4Ioenc7J-yyMem2OlLtjBHFXEC6lHLBoHGQhnSfBv6ujI5i8u3H9nBnjHPIzDjjSCTS8gNgHUs6AFWQ0mZffvtNGHuacCuscW9LdbVjnYy_Snyb9hQU9fA891mRGZya3lld4paOCy8eTgbeiISTZGSCVJI8dHKb5OOpHqmCNpoP4iIp4Y6fyJVh4vvtfdL2nndQxy3cfoeqNAZB01jBDbKWwxIt3Mppv1gBlmK1-CeHWvL3TceJ1rUtcSfAoqnkIozdPQPWJj4kCkxxSKTAXhOhBgPf-isuNLouWT0zCZmhOdq35Lh-JwinYpz16-PprELuqAsFzyk.33fp8abA9AJWXKrW4Xzfpg");
        return JSONUtils.serialize(obj);
    }

    public String getRequestBodyClaims() throws JsonProcessingException {
        Map<String,Object> obj = new HashMap<>();
        obj.put("payload","eyJ4LWhjeC1yZWNpcGllbnRfY29kZSI6IjEtMjk0ODJkZjMtZTg3NS00NWVmLWE0ZTktNTkyYjZmNTY1NzgyIiwieC1oY3gtY29ycmVsYXRpb25faWQiOiI2Njc4YzhlMy04OWI1LTRiOGEtYWU2YS1kNzg2MDc0YjQ4NWQiLCJoLWhjeC10ZXN0X3JhbmRvbSI6InRydWUiLCJ4LWhjeC1zdGF0dXMiOiJyZXF1ZXN0LnF1ZXVlZCIsImgtaGN4LWFwaV9jYWxsX2lkIjoiZWNmM2IwNjYtNWRkZC00MGQxLTlhYmUtNzYzNzFmNTZkMzBlIiwieC1oY3gtcmVxdWVzdF9pZCI6IjA1OTAyMGM3LWVjOWEtNDNjMy04OGNiLTYzOTc5ZGIzZTU5ZCIsImgtaGN4LWNvcnJlbGF0aW9uX2lkIjoiZjZiNTc4ZDItN2NjMC00NTc4LTk4ZTctMDkxMjM4ZTRjNWMwIiwieC1oY3gtdGltZXN0YW1wIjoiMjAyMi0wNS0xMVQxMzowNjoxMi44OTMrMDU6MzAiLCJ4LWhjeC1zZW5kZXJfY29kZSI6IjEtNTIxZWFlYzctOGNiOS00YjZjLThiNGUtNGRiYTMwMGFmNmY0IiwiZW5jIjoiQTI1NkdDTSIsIngtaGN4LXdvcmtmbG93X2lkIjoiMjljMDZlNjgtODNhOS00MzQwLWIwMDItYmEzYjhhZjZmZjlmIiwiYWxnIjoiUlNBLU9BRVAtMjU2IiwieC1oY3gtYXBpX2NhbGxfaWQiOiJhODBkYjJiMS1lODY1LTQzZGYtOGFkYy1kNWUyNWVkOGZlNTgiLCJ4LWhjeC10ZXN0X3JhbmRvbSI6InRydWUifQ.Hn7WrjPPt9dw1pTl0pS_whopS-EGuzfR7_n5n3wh3PkYOT52LigGPoYkDgbT1hXkeW6EHNpCxSyPpIq-4D8k5sHn4OQWdaucLsQybt3Qk9YXDxe7bXaKDM2oAV_ij2g5zz09nev4Jgjb95PzBkXly_tBzoDfAVS7ILnmrj4TjabS02fn1-7hOBxXo-5mHZ931fErq5aRbXa20aiCP3d6GFjnvn24to38RCOLSr65guhjv23ovBPMZwVLmD9qqdrpwsyuNUrmFwVtXriL6J3i0DCCt4SRlTtIeMKFbLShDNCf_y3l7X9CxXLhTMvy0QQlZUEpgUtRRaKDX3CqEEDVzQ.gQS6vD28fmNNCtcv.mPI3v4Zx7UScuiPPEd5tBKy5wsZX829vyft86SSGqVWVrSikRGFFTQqZwxDUv_GeqPwQVZw5k-HjRvzXv4pPau_nF1mV-GhzPCaljXyBD-xBe_wqA9rVmhI3rVTkqdZwKLeeyMEyvT2sKsHrvf6QyQgz3-zyU0jTXYqxwCXV9Mmt4r9bHaYdn8p-BxIv5fsA7iG-_CpAXXkZzABGMbKvYH_3OIqOgNUj2lErbglaoI7AMZspPb-Z2E8SLQE53VDUvwLg0vz7-gabIV-fmVPTHBckcg1mDFReLQQAptAahFdH0XOHTlshO5QYeHexmdXb_lRRM9qwkqpZgIWHNejjAzjtJkNQ_oU7bgbCprnywoEHYDiBG4vOdSRAfrszpKfkQYk8Bga2nzzWINsLGmzTwtTBKo3CcTNkTB6OT0BWEqVoYlapjQ7ZieCJasTHz3Yw2PXqaSfcqTSvXgevpaQVhC0nhJPKxjDt1ySUD1eVZIc1GJxEbm0stF0IOeUbc9Ts7I_rP-TNUCFn9aBjWFjXUkFME0oVvrOGW9OlQhD1rbd1GndNeu1xk4NucJUgPv8YBzeNZKknYIjcL4Caw_KwUzyQmoymkUca5jo_ijYS-_J-8D8wNQRMrZMc7ZYNKAxK9Cy_hAloDrtF-jydDBD7HPMzsl_6ksNAEk3k3valOqn5xBMVJkkDsJfecmTWf0CyerOUtVwurhxSWmVKSGGQV_P8J1uxm6pyNAZtjI5gFtSwWXF2B4ZCg7De-fzBgTH7wULNa-3Has2FYCLlMqiVu4V4ieG-CTPNAN4KUOvJVwkb6mZS31m3zVsV6DFBT2Bb_JmIhba0VkmwQDBzz7AA6x4BBnsPqjOi0CNuY_1RTfr7egiZu1PMRu5GCU4WIfPr_5RgbXEFSJyNMBIu-BipOyKJMrKaQ9CvkgQp1MpLtJtow7BEk5oMQ-jQoJ9bcG3AYseyRj1Zswc6x48PhA8IyIOfrBhBRtLr1AVTF3X3FkRy4ZrY5CJPkdONa0EP0oJLr2y1-a41wDTxJbgcsE-3LEuI4dCul99PhtoexqEH_VAHsBjv0KxUr75SGD-6N2umGFuq3cQuM5EGh8ddNgdG06M0ZLc0zxIRqHQ5DGvfckuseShc6atO9oenEg025y1Se1cDiE6IzCOsBnuIyvL4qCgH0vrgkmb5aX_Mor2vH3Joi6YaZ3bfWmzyVSEiJLV1KlNtg_EeAdOCu47CJLgWmUiMuetFY6VunYX6R3IiAds8_PyUR1fGnqAIGTdhgd-pMHgrCo9XZj8Lk_6ZrGoRBJNobLuWb54wy30tCzUfaEkv-2mys-xJ54gZTEwPyv569eGtp8TbkZN6fyDrxHupwKqIp8t9kS_cAvJ3jJt1JkiVtK3oRCQjX5FkRUjZv1yeHIVnT2CIKNBm9Zd7cjUVZGh1UidAiWltYF0DB7w9Ks9iUI8eVON8RqNUmLT7p4kvgNJKNGSg6874BR7YgCHPosbQSsgsJEy2R9PqJ0N__0EAVystjHkDQvBiIoTxTQvkUP85oVZOd7GvFj7oI5Fsynzie-WVFbAk0DUsd4wFYhHOljix4By879iCROVPNiyCe6AqHcRyDVes_wjxs4ZJSxO_FHI9uWomgJRwSrryBvwZ-KUkXmPU_smo0rP9TBJ93U7lBAEqiNaG0SXcKLJ_1khm_tjin7wFVGq1gEtsgcLFd1FgBf-cLQxURI4ZcIfCT9zldhd2IAuyUuNmw3MHJk6UBuMU8lbmnptSv67XQKqBT0gtFd5wfPdl_u1ghMhLGtkL4rbS5hN_nm8HNwtGqfIHiFYozKRmUm5NUN0Tv93rLcW_4cHRiE8EPndAa5agn75rGRnHFhzlStQAapv73WpUK3cUV-dKRGm85VLPPAtQEOr-5YdNsKNXe8zv9yFtYKrWurT91kzvrexSihvTkTdYLV4e9ODeKSIhiVapYgks54zd9G9GkYOeMsyFRwabnIaiWV4XR9ZOQe2QB-cTWnDiMSh58Zetls5PILxP9M4OD2ySsla_Fp8bqDkxvr1uYpxS5zyRqMhWpNmZ6zpqUgWzV8nuLz7z8rIPmcHVGMmDeQVSyeccerJUoUtFvL9XWWl9XHTpaq6UvBjCdEPIMiG5qx1mxkx7ULQAaRpEpNaBkAo5jSL2GTPHg2aTawKNgk3CZVY5oKJIJ1gBjh6GL84UAue01ifXAAP_cDSyV6uZLjpLj4K11jZSkq3u1-qYNtqlt1CJplzAs1I5u_lRrhO_N4yEdNwz2HGssszjScHNoGALtLQvOi07aRixG9yEBEJ_Xa-EmHqob-XGE2AkUbyj7JY34Wt5SGp6K8oNIyBjLlUwZx8NwMjPuVKCIpzHXDx9r4EyOgpa9u9JnL02RIZDs2U50tyb1sceMXjMVFBMQ3Hq3COefCxk_6YZLYtKQ4mhSeV9Lr-0QWWJBnhajEbianrwK3i_Rjh5o7T9iMZEO78Mt7jjPobqh1BOhfYgI7P484uRAYPzKfTTzGm-HNgpy8uG3wiLVTsEZv2bLq8DIvz4XFwJbRNmqb4iIz99YXkLZWTtlB-M2ur1kxXiFpc4ZYNbEQzlMMp0ZeFW_od0WG-5Gd65BPsBkb31w4NmdNCxLZDhaCd1sn9Y8yHJrWhO8x5lQUxk_4Dd5u3svznXBKMSiris5P79U4x_xjD4jMEGtsz8p1PxZl6aiwhlksaX2ZZQZ-m51Fy7cQUcsr__x4ACAkoj9IbFM1kjGLI2J-8j93Thw4iIhqRDD8xFbZR-F7WyOESRjM5avEoX4j0npHmHRIeZNtMVOqXuuqHY-HLWfllaEQKZ_0TSBjmdkk8ANfjqn_Q24ivMvmlZmR00IZJxxuwz7Cv2mFZXC95VVcPFgUE6iMIwGSzRng681ZNt7NCb8Scoj99xmhQ2MFCwNnNKvJqZaJ9IoBBtbXc0uOVLEtzByYHquVA290jutWVfWMIbR74pTGXdg5KubAK2qns0QjbKDID9aYeHyo7drsFGNrvPE6IxzkZmpyEExU1vdAQ_-W04ItK_jd_3UJ64aLIJkJIq5h1LZw-q8pGOdtb5PYtGAmOa2fob5wMsXsfBv_ev04mZKBuewUlDiGWXVtN5aja-9V8h8dKG6SIO290QPAPt85i_oF03992e-bxZirAnbatkBgcmO55ArMDdYoSbYvEt5UeXHR02tFGbweNPUFkvAXqboQCp5Fneq_LNdYG7SEgwHscrgW9sy5UupxIOCpbWtLIeQ2WgBm9oxHEC3VGlSvhKM7qA8ODVtMmIOT6-UFIEev3BZ9C6IBZ0B-1Xaa3bqUgW4XhDPtembDv0PDdAfXt_l2_J1uOJ6cv77aJTkATgvwHj7ygjlwf5oqLFqzD1393EU6CeLGVyjUxtIUXEoBRuEappotdwFncd5SyN_L7e_fb7ADC0bHZ8iPdUjhGqRCacfHeU15sHY7H9DAgOjMj7dDz9n9yHqzpbx_DFpPh-27sdBlxhOEO01yBXB7giqkW9BtaWgql_RIHqnSJ6cCdNEKaLtUF9v1Oi43qHmpIk3hqFLTMi_oo7QX9-gdTK_WjZ8NMAzS13yoywJm9C0eXySnoKFpgfyVf9pieL9RcJ63YOS-AMppuXVQ-LrNwxvicwlSH5-gK8Z-8wfAE3LVRY2cvR6jfyISQXndraNtN-aLwM0T8BITC31N7lrKgy0htp-pyYH3wiwK5X9PvrRug3zp3Dx8BQA_148gSiIc__vY88cuMDmtE_sP9aQWopwr8wIgpi3mDHVd46WDjRO9ZBV5i_Q1Y494q53EsjQC2h8XNOcML98BPCGpTCWkphZGE7svRR_B2KH2HyHBo0lkxUGtAUSbDQLolL24lBY81X0peOelqwrZslvMm5FMbI7KY-r3b9m6qisdD77ERN8JkNqHDPSFDJkwsYW0GkytVme8StmkYYLKpjw2PKrHN1DQJrexq7TdsY8oBYWjBiJDnkEC7Wb6W5goKuVwVTzT_RictQz-wC8BJzbp5C4SDvcL8yGDDXqwOO-n6vRdLgCClLfo0v0UOY8F7fv_yGOK0aZUtszy-tEkL42hPbOgAPIs1eC98qN-UiGJ6aSZNpUzcyUv74oXlD57f9ifU3o9F7LkmIsxpZOKW1_5c6ihL0CUr3-8Z9a3wXx_8w21muV59nOmoYCz9PVWeZ3x9-2_CU2RZLtUKwm1mRDgwXLitZn7_QDPqLK1z244s_4fLrW9ZSZix9Xy8TrSGO1ufTeOLlGpMgLO74_0LXgwT_SPA6E0muEzqdwGGW-G0Nnx7hyJncIOsAK71I8g9681qUCl31Uh0qn71TacyBjhOi0GUiA0s8_ZOZFbdswxOKhWLH1wJ_4OYhL9S-nv1FJ8uV5KX8QYFw3cyMqTzOgw9wHzZsHvsne0aN-58SDf7CHTeBX8KRAzTLQaI-6neHo6OjiH3w0g3a-11kSalYEDuz3Im_CsIvevKruFV2odo5Lxt_eHKzk2Y2O8WSUbLTaPx_w_yJt4GnjPYJHIZxgmWVRimCjTk4NK8PCMI2pHpyotwUorLSnee9UAdLMwYMK1wqf0ELJo29Ko6e3noWbjuiuu1FDp62kUtMhgvCwTqRMX7P7obAcVGNEEItjMjSRJYiEL_PR11Y_HoP395R8NVsd1QjUs-QLFU19-P6JyJlPyfzKp4_iqZ2r65WoVvoM30SSUUBXXm98K9jX_bVpq7zBTY6qLF2tvUNVnRoQzr1M0oOKxxU8n2o2d2os-n0IoFJ1TMWkuhmMH0gD4-6CFb1A0YN-HVwyJ9wJJ_lR8HiUcTMNnSMGAMiDz3DOpV0FV9AdsG1AkTdNy8K94Lj8imbjGGkh3QH_UYsWQFJlU6vM-cQxEdo50zTNKGerLxpb_E7mL2M6RprMbzCvW8Nk561VPWWRaAfqPlaAgLRZRgOm0_KH-zExPlZhwy8tk3NkKoumI-eOGIWhmGHFQXXkd02PEZ_w8aXrwSkCpUgvy1gb631mIorTZIM2dKHTngvXYyLEyrDPhvb0PJlVfRFWBBohuSTZBHQlsryEP7RcdlUhlVWa7aKIYELZxbkQUhPtnSmufgA0eAhUJYab64b71qV0w4fU5etXiwcTc1Wy5dAyBCcuDvksvuXCLBnl7YIp3OXVb8O8w9e_VjQ_UzHH4vH30oaBSUylgmpyBI7EOzJUH6Abf44XIzzU5zPVM-twweNeFvT_oMEAQoeLkAaHLkTNqJWEFKiPAuQo78ra4hNxALEw7CkCs1z4mhI2WHHpIKSowoMIUhwLvMtmBRPjYEbalBRP9Tqtc0MBzUPAMwZbEoyc8kNYh1Al1V1oKem1hBzcW74lEpyErKPmIqOyuAcc0xmE7qpBnNPCmYgX5jyJqmKSpSwE1W_xDkSNnQfqwkTIn79Pg6tw_KRznZ5953qbqM-PgARrW3VmTTUxhnqwYusL9J7EU_l7CLvjqwJpWVg6Z9Dh4s_MTSEVU3Fk6JQKs-un_MxWcjV5v3eUqA8rCVXZJLrtG9XQRPLoOy6TtxTUpKjX-Cr5QqHix2tRs2Iqzlj4notZsb_9aDPftH6B7pIKS-C8qWZ2-KeYYz8NaJ7baeARUF8sMEOhM5jtWDY_lT9mgmtK4pP2LBuQomkydRloGgQDBs624UliQ48pCvKNNcM7ImohabA44vX3nEpzKwOrm-s8Q7Dxl90CweN7MXEsx1Gu4Jf1eOidl9u7joU3TrRrF3diVPL3dZfMR_Cp-RoOu1kK4MSUumIU5lMJm78fLsaNbmvNvtIAjjH0cwLkD94SlDZ-nCuAJc_An2ayPIkHJCth9rBsckh9PdN8YPuAKchatUVZbJ2N-1bPey7jCi7ssrOzQv2D0rATz3hNUIH0ViVo3vcq8JfgGqBWqbDSy6V6YhuGIEZjxkwrepoDsn7m-ek1I61NOetrdiVZnmRqeN6bhLUlNXA1a9kjaFpOQcRHvpYyRQph72IqgX_lKZnDzMfc19d5GW2NEvm-TwEgpkRXVQPXNBK3Ba8aVSosD9N-wQ7RbbOUBcFaI3iplJtEOjuGbMQLrHmNcNo2_3LYxultriwtNnQ2eBKpAbqMW2hz9J5AIPs5jdYhCXXhS3CHKnIob5kds7z-gOJXt7IVZXnnmunivAYkVxSiX6LtTnZdyDlozVeJEOCyY4gDkn-ysaPDODziA1vOmPymgYd9qhAY5zb0DigWZf1bKK-KPNTA4d6M_K4zHAHg0nXzu28Av90yDVSTKViz6wWT04h42EEL9n1a9lh3aNIuXOnpU6PP7RZRnpc0DDNc_gg9rYp958UE242YZfaMv91pkNWmtMJJFnJkcEUoYvYHEuGwDUfcrQs961gth8-72wY82HevAKAzaZd8YRY7Tiu7n-6ZVC3D9mvMag9zvjEWecafSgqUHEOpskfrE1MjDW_Do6EXzrhdyUGNgWJz1ByHT_n5l6mrwfAKOVAHf3qSH5Nr0Faq3FOdHHlfPvXCYSlgYHc9hLLRMblpB6layIKGFEhf4tqhOjoNUgxggohMNcVAzJmTrVRbHAFRP_PvapY0cDEWfCQE2UF0BfrEb2dhdUtExvbgvnxqvKT797fZSUFxDBfIdcKJkNKNqzgzWop4TdbnmJZvB2ZPchRc8qnTFIAu9oBaBM0TZUrLPPfjzrlAYO1cJdihLWeEBwXJr4QkIFc_YQtENTZvocu2KsIW3eRQ3Ft1J4m1rh7s2FZGrpyyPoRm1Zqna4NQK1UOaLxq0149-SvHQ9L0XXDwtiJZX5_eTn8MkFDtYB7rBX6Su0L1K9k1v1Siuk.3q0Yc5hhiTumvQ-ZlMqIFA");
        return JSONUtils.serialize(obj);
    }

    public String getBadRequestBody() throws JsonProcessingException {
        Map<String,Object> obj = new HashMap<>();
        obj.put("payload","eyJlbmMiOiJBMjU2R0NNIiwKImFsZyI6IlJTQS1PQUVQIiwKIngtaGN4LXJlY2lwaWVudF9jb2RlIjoiMS0yNzk5YjZhNC1jZjJkLTQ1ZmUtYTVlMS01ZjFjODI5NzllMGQiLAoieC1oY3gtcmVxdWVzdF9pZCI6IjI2YjEwNjBjLTFlODMtNDYwMC05NjEyLWVhMzFlMGNhNTA5MSIsCiJ4LWhjeC1jb3JyZWxhdGlvbl9pZCI6IjVlOTM0ZjkwLTExMWQtNGYwYi1iMDE2LWMyMmQ4MjA2NzRlMSIsCiJ4LWhjeC10aW1lc3RhbXAiOiIyMDIyLTAxLTA2VDA5OjUwOjIzKzAwIiwKIngtaGN4LXN0YXR1cyI6InJlcXVlc3QuaW5pdGlhdGUiLAoieC1oY3gtd29ya2Zsb3dfaWQiOiIxZTgzLTQ2MGEtNGYwYi1iMDE2LWMyMmQ4MjA2NzRlMSIsCiJ4LWhjeC1kZWJ1Z19mbGFnIjoiSW5mbyIsCiJ4LWhjeC1lcnJvcl9kZXRhaWxzIjp7ImVycm9yLmNvZGUiOiAiYmFkLmlucHV0IiwgImVycm9yLm1lc3NhZ2UiOiAiUHJvdmlkZXIgY29kZSBub3QgZm91bmQiLCAidHJhY2UiOiAiIn0sCiJ4LWhjeC1kZWJ1Z19kZXRhaWxzIjp7ImVycm9yLmNvZGUiOiAiYmFkLmlucHV0IiwgImVycm9yLm1lc3NhZ2UiOiAiUHJvdmlkZXIgY29kZSBub3QgZm91bmQiLCJ0cmFjZSI6IiJ9LAoieC1oY3gtc3RhdHVzX2ZpbHRlcnMiOnsicmVxdWVzdF9pZCI6IjI2YjEwNjBjLTFlODMtNDYwMC05NjEyLWVhMzFlMGNhNTEwMSJ9LAoiandzX2hlYWRlciI6eyJ0eXAiOiJKV1QiLCAiYWxnIjoiUlMyNTYifSwKImp3ZV9oZWFkZXIiOnsiYWxnIjoiUlNBLU9BRVAiLCJlbmMiOiJBMjU2R0NNIn0KfQ==.6KB707dM9YTIgHtLvtgWQ8mKwboJW3of9locizkDTHzBC2IlrT1oOQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.AxY8DCtDaGlsbGljb3RoZQ.KDlTtXchhZTGufMYmOYGS4HffxPSUrfmqCHXaI9wOGY.Mz-VPPyU4RlcuYv1IwIvzw");
        return JSONUtils.serialize(obj);
    }

    public String getExceptionRequestBody() throws JsonProcessingException {
        Map<String,Object> obj = new HashMap<>();
        obj.put("payload","eyJlbmMiOiJBMjU2R0NNIiwKImFsZyI6IlJTQS1PQUVQIiwKIngtaGN4LXNlbmRlcl9jb2RlIjoiMS05ODc1Ni1jZjJkLTQ1ZmUtYTVlMS01ZjFjODI5NzllMGQiLAoieC1oY3gtcmVjaXBpZW50X2NvZGUiOiIxLTI3OTliNmE0LWNmMmQtNDVmZS1hNWUxLTVmMWM4Mjk3OWUwZCIsCiJ4LWhjeC1hcGlfY2FsbF9pZCI6MTIzLAoieC1oY3gtdGltZXN0YW1wIjoiMjAyMi0wMS0wNlQwOTo1MDoyMyswMCIsCiJ4LWhjeC13b3JrZmxvd19pZCI6IjFlODMtNDYwYS00ZjBiLWIwMTYtYzIyZDgyMDY3NGUxIgp9.6KB707dM9YTIgHtLvtgWQ8mKwboJW3of9locizkDTHzBC2IlrT1oOQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.AxY8DCtDaGlsbGljb3RoZQ.KDlTtXchhZTGufMYmOYGS4HffxPSUrfmqCHXaI9wOGY.Mz-VPPyU4RlcuYv1IwIvzw");
        return JSONUtils.serialize(obj);
    }

    public String getHeadersMissingRequestBody() throws JsonProcessingException {
      Map<String,Object> obj = new HashMap<>();
      obj.put("payload","eyJlbmMiOiJBMjU2R0NNIiwKImFsZyI6IlJTQS1PQUVQIiwKIngtaGN4LXJlY2lwaWVudF9jb2RlIjoiMS0yNzk5YjZhNC1jZjJkLTQ1ZmUtYTVlMS01ZjFjODI5NzllMGQiLAoieC1oY3gtcmVxdWVzdF9pZCI6IjI2YjEwNjBjLTFlODMtNDYwMC05NjEyLWVhMzFlMGNhNTA5MSIsCiJ4LWhjeC1jb3JyZWxhdGlvbl9pZCI6IjVlOTM0ZjkwLTExMWQtNGYwYi1iMDE2LWMyMmQ4MjA2NzRlMSIsCiJ4LWhjeC10aW1lc3RhbXAiOiIyMDIxLTEwLTI3VDIwOjM1OjUyLjYzNiswNTMwIiwKIngtaGN4LXN0YXR1cyI6InJlcXVlc3QuaW5pdGlhdGUiLAoieC1oY3gtd29ya2Zsb3dfaWQiOiIxZTgzLTQ2MGEtNGYwYi1iMDE2LWMyMmQ4MjA2NzRlMSIsCiJ4LWhjeC1kZWJ1Z19mbGFnIjoiSW5mbyIsCiJ4LWhjeC1lcnJvcl9kZXRhaWxzIjp7ImVycm9yLmNvZGUiOiAiYmFkLmlucHV0IiwgImVycm9yLm1lc3NhZ2UiOiAiUHJvdmlkZXIgY29kZSBub3QgZm91bmQiLCAidHJhY2UiOiAiIn0sCiJ4LWhjeC1kZWJ1Z19kZXRhaWxzIjp7ImVycm9yLmNvZGUiOiAiYmFkLmlucHV0IiwgImVycm9yLm1lc3NhZ2UiOiAiUHJvdmlkZXIgY29kZSBub3QgZm91bmQiLCJ0cmFjZSI6IiJ9LAoiandzX2hlYWRlciI6eyJ0eXAiOiJKV1QiLCAiYWxnIjoiUlMyNTYifSwKImp3ZV9oZWFkZXIiOnsiYWxnIjoiUlNBLU9BRVAiLCJlbmMiOiJBMjU2R0NNIn0KfQ==.6KB707dM9YTIgHtLvtgWQ8mKwboJW3of9locizkDTHzBC2IlrT1oOQ.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.AxY8DCtDaGlsbGljb3RoZQ.KDlTtXchhZTGufMYmOYGS4HffxPSUrfmqCHXaI9wOGY.Mz-VPPyU4RlcuYv1IwIvzw");
      return JSONUtils.serialize(obj);
    }

    public Response validHealthResponse() {
        return new Response("healthy",true);
    }
}
