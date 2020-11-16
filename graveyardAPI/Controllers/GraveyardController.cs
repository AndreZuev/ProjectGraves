using System.IO;
using System.Text.Json;
using graveyardAPI.Models;
using Microsoft.AspNetCore.Mvc;

namespace graveyardAPI.Controllers
{

    [Route("[controller]")]
    [ApiController]
    public class GraveyardController : ControllerBase
    {
        private GraveyardModel graveyardModel;
        private GraphModel graphModel;

        public GraveyardController()
        {
            using (var sr = new StreamReader("graveyard.json"))
            {
                string json = sr.ReadToEnd();
                graveyardModel = JsonSerializer.Deserialize<GraveyardModel>(json);
            }
            using (var sr = new StreamReader("graph.json"))
            {
                string json = sr.ReadToEnd();
                graphModel = JsonSerializer.Deserialize<GraphModel>(json);
            }
        }

        /// <summary>
        /// Get all information about the graveyard.
        /// </summary>
        [ProducesResponseType(typeof(GraveyardModel), 200)]
        [HttpGet()]
        public GraveyardModel GetGraveyard()
        {
            return graveyardModel;
        }

        /// <summary>
        /// Get all edges for the graveyard.
        /// </summary>
        [ProducesResponseType(typeof(GraphModel), 200)]
        [HttpGet("/Graph")]
        public GraphModel GetGraph()
        {
            return graphModel;
        }
    }

}