using System.Text.Json.Serialization;

namespace graveyardAPI.Models
{

    public interface IEdgesModel
    {
        Edge[] edges { get; set; }
    }

    public class EdgesModel : IEdgesModel
    {
        [JsonPropertyName("edges")]
        public Edge[] edges { get; set; }
    }

    public interface IEdge
    {
        string Source { get; set; }
        string Destination { get; set; }
        double Weight { get; set; }
    }

    public class Edge : IEdge
    {
        [JsonPropertyName("src")]
        public string Source { get; set; }
        [JsonPropertyName("dest")]
        public string Destination { get; set; }
        [JsonPropertyName("weight")]
        public double Weight { get; set; }
    }

}