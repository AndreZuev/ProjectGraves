using System.Text.Json.Serialization;

namespace graveyardAPI.Models
{

    public interface IGraphModel
    {
        Edge[] edges { get; set; }
        Vertex[] vertices { get; set; }
    }

    public class GraphModel : IGraphModel
    {
        [JsonPropertyName("edges")]
        public Edge[] edges { get; set; }
        [JsonPropertyName("vertices")]
        public Vertex[] vertices { get; set; }
    }

    public interface IVertex
    {
        string Label { get; set; }
        double Latitude { get; set; }
        double Longitude { get; set; }
    }

    public class Vertex : IVertex
    {
        [JsonPropertyName("label")]
        public string Label { get; set; }
        [JsonPropertyName("latitude")]
        public double Latitude { get; set; }
        [JsonPropertyName("longitude")]
        public double Longitude { get; set; }
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