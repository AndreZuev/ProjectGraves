using System.Text.Json.Serialization;

namespace graveyardAPI.Models
{

    public interface IGraveyardModel
    {
        GraveyardBlock[] Blocks { get; set; }
    }

    public class GraveyardModel : IGraveyardModel
    {
        [JsonPropertyName("blocks")]
        public GraveyardBlock[] Blocks { get; set; }
    }

    public interface IGraveyardBlock
    {
        string ID { get; set; }
        double Latitude { get; set; }
        double Longitude { get; set; }
        GraveyardLot[] Lots { get; set; }
    }

    public class GraveyardBlock : IGraveyardBlock
    {
        [JsonPropertyName("id")]
        public string ID { get; set; }
        [JsonPropertyName("latitude")]
        public double Latitude { get; set; }
        [JsonPropertyName("longitude")]
        public double Longitude { get; set; }
        [JsonPropertyName("lots")]
        public GraveyardLot[] Lots { get; set; }
    }

    public interface IGraveyardLot
    {
        int lotNumber { get; set; }
        Grave[] Graves { get; set; }
    }

    public class GraveyardLot : IGraveyardLot
    {
        [JsonPropertyName("lotNumber")]
        public int lotNumber { get; set; }
        [JsonPropertyName("graves")]
        public Grave[] Graves { get; set; }
    }

    public interface IGrave
    {
        string Name { get; set; }
        string DateOfDeath { get; set; }
        string Age { get; set; }
    }

    public class Grave : IGrave
    {
        [JsonPropertyName("name")]
        public string Name { get; set; }
        [JsonPropertyName("dateOfDeath")]
        public string DateOfDeath { get; set; }
        [JsonPropertyName("age")]
        public string Age { get; set; }
    }

}