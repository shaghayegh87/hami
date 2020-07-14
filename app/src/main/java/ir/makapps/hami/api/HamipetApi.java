package ir.makapps.hami.api;import java.io.File;import java.util.List;import io.reactivex.Single;import ir.makapps.hami.model.BannerModel;import ir.makapps.hami.model.BodyModel;import ir.makapps.hami.model.CityModel;import ir.makapps.hami.model.HamiResponse;import ir.makapps.hami.model.MainBriefModel;import ir.makapps.hami.model.MainModel;import ir.makapps.hami.model.ValidationModel;import okhttp3.MultipartBody;import okhttp3.RequestBody;import retrofit2.Call;import retrofit2.http.Field;import retrofit2.http.FormUrlEncoded;import retrofit2.http.GET;import retrofit2.http.Header;import retrofit2.http.Multipart;import retrofit2.http.POST;import retrofit2.http.Part;public interface HamipetApi {    @POST("main/GetMainBriefList")    @FormUrlEncoded    Single<HamiResponse<List<MainBriefModel>>> GetMainBriefList(            @Header("Authorization") String token,            @Field("stateId") int stateId,            @Field("desc") String desc,            @Field("pageIndex") int pageIndex);    @POST("main/GetMainDetail")    @FormUrlEncoded    Single<HamiResponse<MainModel>> getDetail(            @Header("Authorization") String token,            @Field("id") int id);    @POST("user/GetValidationCode")    @FormUrlEncoded    Single<ValidationModel> getValidationCode(@Field("Mobile") String mobile,                                              @Field("SecurityNumber") int securityNumber);    @POST("user/login")    @FormUrlEncoded    Single<HamiResponse<BodyModel>> getToken(@Field("Mobile") String mobile,                                          @Field("ValidationCode") int validationCode);    @POST("main/SaveBookmark")    @FormUrlEncoded    Single<HamiResponse> saveBookmark(@Header("Authorization") String token,                                      @Field("mainId") int id);    @POST("main/DeleteBookmark")    @FormUrlEncoded    Single<HamiResponse> deleteBookmark(@Header("Authorization") String token,                                        @Field("mainId") int id);    @POST("baseData/GetStates")    Single<HamiResponse<List<CityModel>>> getStates(@Header("Authorization") String token);    @POST("main/GetUserBookmarks")    Single<HamiResponse<List<MainBriefModel>>> GetUserBookmarks(@Header("Authorization") String token);    @POST("main/GetUserMains")    Single<HamiResponse<List<MainBriefModel>>> GetUserMains(@Header("Authorization") String token);    @Multipart    @POST("main/InsertNewSupport")    Single<HamiResponse<MainModel>> InsertNewSupport(            @Header("Authorization") String token,            @Part List<MultipartBody.Part> Files,            @Part("title") RequestBody title,            @Part("Description") RequestBody Description,            @Part("Address") RequestBody Address,            @Part("StateId") int StateId,            @Part("Latitude") RequestBody Latitude,            @Part("Longitude") RequestBody Longitude    );}